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
    private final PutCardService putCardService;

    private final MemberRepository memberRepository;

    private final CardService cardService;
    @GetMapping("/cards")
    public ResponseEntity<?> recommendCard(@AuthenticationPrincipal CustomUserDetails member) {
        Member mem;
        if (member == null) {
            mem = memberRepository.findByNickname("test"); // for test only
        } else {
            mem = member.getMember();
        }
        List<CardSimpleDto> cardDtoList = cardService.getCards(mem);
        return new ResponseEntity<>(cardDtoList, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getCard(@AuthenticationPrincipal CustomUserDetails member, @RequestParam Long id) {
        Member mem;
        if (member == null) {
            mem = memberRepository.findByNickname("test"); // for test only
        } else {
            mem = member.getMember();
        }
        CardDto card = cardService.getCard(id);
        return new ResponseEntity<>(card, HttpStatus.OK);
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
