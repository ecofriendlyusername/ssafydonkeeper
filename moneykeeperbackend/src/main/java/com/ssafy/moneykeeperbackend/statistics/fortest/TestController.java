package com.ssafy.moneykeeperbackend.statistics.fortest;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.service.UpdateDataService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController {
    private final UpdateDataService updateDataService;
    private final TestService testService;

    private final MemberRepository memberRepository;

    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;

    private final SpendingService spendingService;
    @GetMapping("")
    @ApiOperation(value = "테스트할 때 더미데이터(유저 등)를 넣기 위한 엔드포인트. \n"
            +"현재는 소비, 수입은 생성하지 않음, 통계를 위한 전체소비, 전체수입만 생성됨(뭔말인지 알필요 X)", notes = "생성된 유저 중 한 유저의 아이디를 값으로 돌려줌. \n" +
            "이 아이디를 comparemonths compareusers 엔드포인트에 접근할 때 사용하면 됨")
    public ResponseEntity<?> initForTest() { // 나중에 바꿀 것
        LocalDate now = LocalDate.now();

        LocalDate lastMonth = now.minusMonths(1);

        LocalDate end = LocalDate.of(lastMonth.getYear(),lastMonth.getMonth(),1);
        LocalDate start = end.minusMonths(2);
        HashMap<String, MajorSpendingClassification> hm = testService.initCommonForTest();

        Long testMemberId = testService.generateMockMemberWithString("test", hm,start,end);

        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int rn = random.nextInt(100000000)+1;
            testService.generateMockMemberWithString(String.valueOf(rn),hm,start,end);
        }

        updateDataService.updateSpendingCompData();

        return new ResponseEntity<Long>(testMemberId, HttpStatus.OK);
    }
}
