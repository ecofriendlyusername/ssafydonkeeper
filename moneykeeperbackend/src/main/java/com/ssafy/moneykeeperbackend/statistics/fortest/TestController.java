package com.ssafy.moneykeeperbackend.statistics.fortest;

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
