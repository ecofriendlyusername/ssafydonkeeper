package com.ssafy.moneykeeperbackend.card.controller;

import com.ssafy.moneykeeperbackend.card.dto.CardDto;
import com.ssafy.moneykeeperbackend.card.dto.CardSimpleDto;
import com.ssafy.moneykeeperbackend.card.service.CardService;
import com.ssafy.moneykeeperbackend.card.service.PutCardService;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
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
    private final CardService cardService;
    @GetMapping("/cards")
    public ResponseEntity<?> recommendCard(@AuthenticationPrincipal CustomUserDetails member) {
        List<CardSimpleDto> cardDtoList = cardService.getCards(member.getMember());
        return new ResponseEntity<>(cardDtoList, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getCard(@RequestParam Long id) {
        CardDto card = cardService.getCard(id);
        return new ResponseEntity<>(card, HttpStatus.OK);
    }
}
