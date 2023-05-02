package com.ssafy.moneykeeperbackend.statistics.fortest;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.service.UpdateDataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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
    @Operation(summary = "호출시 영화,배달,외식 총 3개의 카테고리가 들어감, 테스트용 멤버도 생성됨(이후 id 1쓰면 됨)", description = "카테고리 및 멤버 생성"
            , responses = {
    })
    public ResponseEntity<?> initForTest() { // 나중에 바꿀 것
        testService.initCommonForTest();

        Long testMemberId = testService.generateMockMemberWith("test");

        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int rn = random.nextInt(100000000)+1;
            testService.generateMockMemberWith(String.valueOf(rn));
        }

        updateDataService.updateSpendingCompData();

        return new ResponseEntity<Long>(testMemberId, HttpStatus.OK);
    }
}
