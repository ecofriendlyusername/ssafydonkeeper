package com.ssafy.moneykeeperbackend.statistics.controller;

import com.ssafy.moneykeeperbackend.statistics.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistics")
public class StatController {
    private final StatService statService;
    @GetMapping("/comparemonths/{months}")
    public ResponseEntity<?> compareWithRecentXMonths(@PathVariable int months, @RequestPart String id) {
        Map<String,double[]> map = statService.compareWithRecentXMonths(months, Long.parseLong(id));

        return new ResponseEntity<Map<String,double[]>>(map, HttpStatus.OK);
    }
}
