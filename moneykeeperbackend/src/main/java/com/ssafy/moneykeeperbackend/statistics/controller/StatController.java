package com.ssafy.moneykeeperbackend.statistics.controller;

import com.ssafy.moneykeeperbackend.statistics.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/statistics")
public class StatController {
    private final StatService statService;
    @GetMapping("/comparemonths/{months}")
    public ResponseEntity<?> compareWithRecentXMonths(@PathVariable int months) {
        Map<String,int[]> map = statService.compareWithRecentXMonths(months);

        return new ResponseEntity<Map<String,int[]>>(map, HttpStatus.OK);
    }
}
