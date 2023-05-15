package com.ssafy.moneykeeperbackend.statistics.controller;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;
import com.ssafy.moneykeeperbackend.statistics.dto.*;
import com.ssafy.moneykeeperbackend.statistics.service.StatService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatController {
    private final StatService statService;
    @GetMapping("/comparemonths/{months}")
    @ApiOperation(value = "test", notes = "test")
    public ResponseEntity<?> compareWithRecentXMonths(@PathVariable int months, @AuthenticationPrincipal CustomUserDetails member) {
        List<CompareWithRecentXDto> li = statService.compareWithRecentXMonths(months,member.getMember());
        return new ResponseEntity<List<CompareWithRecentXDto>>(li, HttpStatus.OK);
    }

    @GetMapping("/spending/{year}/{month}")
    public ResponseEntity<?> getMonthSpending(@PathVariable int year, @PathVariable int month, @AuthenticationPrincipal CustomUserDetails member) {
        MonthSpendingRecordDto msr = statService.getMonthSpending(year,month,member.getMember());
        return new ResponseEntity<MonthSpendingRecordDto>(msr,HttpStatus.OK);
    }



    @GetMapping("/compareusers/{year}/{month}")
    public ResponseEntity<?> compareWithUsers(@PathVariable int year, @PathVariable int month, @AuthenticationPrincipal CustomUserDetails member) {
        CompareWithUserDto compareWithUserDto = statService.compareWithUsers(year,month,member.getMember());
        if (compareWithUserDto == null) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<CompareWithUserDto>(compareWithUserDto, HttpStatus.OK);
    }

    @GetMapping("/monthlyspendingbycat/{year}/{month}")
    public ResponseEntity<?> thisMonthSpendingByCategory(@PathVariable int year, @PathVariable int month, @AuthenticationPrincipal CustomUserDetails member) {
        List<MSRCDto> msrcDtoList = statService.thisMonthSpendingByCategory(year,month,member.getMember());
        if (msrcDtoList == null) {
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<MSRCDto>>(msrcDtoList, HttpStatus.OK);
    }
}
