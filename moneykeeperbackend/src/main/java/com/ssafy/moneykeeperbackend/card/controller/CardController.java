package com.ssafy.moneykeeperbackend.card.controller;

import com.ssafy.moneykeeperbackend.card.dto.CardDto;
import com.ssafy.moneykeeperbackend.card.service.CardService;
import com.ssafy.moneykeeperbackend.card.service.PutCardService;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/card")
public class CardController {
    private final PutCardService putCardService;

    private final CardService cardService;
    @GetMapping("/cards")
    public ResponseEntity<?> recommendCard(@AuthenticationPrincipal CustomUserDetails member) {
        List<CardDto> cardDtoList = cardService.getCards(member.getMember());
        return new ResponseEntity<>(cardDtoList, HttpStatus.OK);
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
}
