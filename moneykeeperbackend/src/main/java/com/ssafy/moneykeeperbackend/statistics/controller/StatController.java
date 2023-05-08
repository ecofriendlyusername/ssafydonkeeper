package com.ssafy.moneykeeperbackend.statistics.controller;

import com.ssafy.moneykeeperbackend.statistics.dto.CompareWithRecentXDto;
import com.ssafy.moneykeeperbackend.statistics.dto.MonthIncomeRecordDto;
import com.ssafy.moneykeeperbackend.statistics.dto.MonthSpendingRecordDto;
import com.ssafy.moneykeeperbackend.statistics.dto.TotalAndComparedDto;
import com.ssafy.moneykeeperbackend.statistics.service.StatService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatController {
    private final StatService statService;
    @GetMapping("/comparemonths/{months}")
    @ApiOperation(value = "최근 months달과 현재 달의 소비를 category 별로 비교", notes = "최근 months달 간의 평균 소비와 이번 달 소비 비교  \n" +
            "예를 들어서, months가 4라면 2023년 1-4월의 평균소비를 recentXAvg에, \n" +
            "5월의 소비를 thisMonth에 소비항목별로 보내줌 \n")
    public ResponseEntity<?> compareWithRecentXMonths(@PathVariable int months, @RequestPart String id) {
        List<CompareWithRecentXDto> li = statService.compareWithRecentXMonths(months, Long.parseLong(id));
        return new ResponseEntity<List<CompareWithRecentXDto>>(li, HttpStatus.OK);
    }

    @GetMapping("/spending/{year}/{month}")
    @ApiOperation(value = "year년 year월 지출", notes = "year년 month월의 총소비를 보내줌(숫자만 보내줌)")
    public ResponseEntity<?> getMonthSpending(@PathVariable int year, @PathVariable int month, @RequestPart String id) {
        MonthSpendingRecordDto msr = statService.getMonthSpending(year,month,Long.valueOf(id));
        return new ResponseEntity<Integer>(msr.getAmount(),HttpStatus.OK);
    }

    @GetMapping("/income/{year}/{month}")
    @ApiOperation(value = "year년 year월 수입", notes = "year년 month월의 총수입을 보내줌(숫자만 보내줌)")
    public ResponseEntity<?> getMonthIncome(@PathVariable int year, @PathVariable int month, @RequestPart String id) {
        MonthIncomeRecordDto mir = statService.getMonthIncome(year,month,Long.valueOf(id));
        return new ResponseEntity<Integer>(mir.getAmount(),HttpStatus.OK);
    }

    @GetMapping("/compareusers/{year}/{month}")
    @ApiOperation(value = "year년 year월에 나와 소득구간이 같았던 사용자들의 소비 평균과 나의 총 소비", notes = "year년 month달에 나와 같은 수입구간에 있는 유저들은 평균 얼마를 썼고(groupAvg) 나는 얼마를 썼는지(total) 보내줌 \n 소득구간 - > base이상 below미만의 수입")
    public ResponseEntity<?> compareWithUsers(@PathVariable int year, @PathVariable int month, @RequestPart String id) {
        TotalAndComparedDto tcd = statService.compareWithUsers(year,month,Long.parseLong(id));
        if (tcd == null) {
            // for now
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<TotalAndComparedDto>(tcd, HttpStatus.OK);
    }
}
