package com.ssafy.moneykeeperbackend.record.controller;

import com.ssafy.moneykeeperbackend.record.dto.SpendingRequestDto;
import com.ssafy.moneykeeperbackend.record.service.SpendingRecordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/record")
public class RecordController {

    private final SpendingRecordService spendingRecordService;
    @PostMapping("/spending")
    @Operation(summary = "지출 기록", description = "지출을 기록한다"
            , responses = {
    })
    public ResponseEntity<?> recordSpending(@RequestPart SpendingRequestDto spending) {
        // take name, comment
        spendingRecordService.recordSpending(spending);
        return ResponseEntity.ok().build();
    }


    // for test
    @PostMapping("/spending/{category}")
    @Operation(summary = "카테고리 추가", description = "지출을 기록한다"
            , responses = {
    })
    public ResponseEntity<?> addCatForTest(@PathVariable String category) {
        // take name, comment
        spendingRecordService.addCatForTest(category);
        return ResponseEntity.ok().build();
    }

}
