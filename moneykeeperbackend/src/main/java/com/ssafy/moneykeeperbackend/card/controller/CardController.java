package com.ssafy.moneykeeperbackend.card.controller;

import com.ssafy.moneykeeperbackend.card.service.PutCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/card")
public class CardController {
    private final PutCardService putCardService;
    @GetMapping("/cards")
    public ResponseEntity<?> recommendCard(@RequestPart Long id) {

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/savecards")
    public ResponseEntity<?> saveCards() throws Exception {
        putCardService.saveCards();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() throws Exception {
        return new ResponseEntity<>("tested", HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<?> testPost() throws Exception {
        return new ResponseEntity<>("tested", HttpStatus.OK);
    }

//    @GetMapping("/testenum")
//    public ResponseEntity<?> testenum() throws Exception {
//        putCardService.testenum();
//        return new ResponseEntity<>("tested", HttpStatus.OK);
//    }
}
