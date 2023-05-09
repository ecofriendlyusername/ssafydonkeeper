package com.ssafy.moneykeeperbackend.card.throwaway;


import com.ssafy.moneykeeperbackend.card.dto.CardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transfer")
public class FileTransferController {

    private final FileTransferService fileTransferService;
    @GetMapping("")
    public ResponseEntity<?> transferFile() {
        fileTransferService.transferFile();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
