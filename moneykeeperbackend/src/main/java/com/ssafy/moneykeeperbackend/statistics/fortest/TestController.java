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
    @ApiOperation(value = "test", notes = "test")
    public ResponseEntity<?> initForTest() { // 나중에 바꿀 것
        LocalDate now = LocalDate.now();

        LocalDate lastMonth = now.minusMonths(1);

        LocalDate end = LocalDate.of(lastMonth.getYear(),lastMonth.getMonth(),1);
        LocalDate start = end.minusMonths(2);
        HashMap<String, MajorSpendingClassification> hm = testService.initCommonForTest();

        Long testMemberId = testService.generateMockMemberWithString("test", hm,start,end);

        for (int i = 0; i < 30; i++) {
            Random random = new Random();
            int rn = random.nextInt(1000000000)+1;
            testService.generateMockMemberWithString(String.valueOf(rn),hm,start,end);
        }

        updateDataService.updateSpendingCompData();

        return new ResponseEntity<Long>(testMemberId, HttpStatus.OK);
    }
}
