package com.ssafy.moneykeeperbackend.challenge.controller;


import com.ssafy.moneykeeperbackend.challenge.dto.request.ChallengeRequestDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeDetailResponseDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeDetailResponseFinishedDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeListDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeMemberDetailResponse;
import com.ssafy.moneykeeperbackend.challenge.service.ChallengeService;
import com.ssafy.moneykeeperbackend.exception.challenge.ChallengeException;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    /*
     * 챌린지 등록
     *
     * @date 2023.05.11
     * @author 양윤정
     * */


    @PostMapping()
    public ResponseEntity<?> addChallenge(@RequestBody ChallengeRequestDto challengeRequest) throws Exception {

        try {
            challengeService.addChallenge(challengeRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ChallengeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*
     * 챌린지 목록조회
     *
     *
     * 참여 가능한(아직 모집중인) 챌린지들 보여주기
     *
     * @date 2023.05.11
     * @author 양윤정
     * */


    @GetMapping("/list")
    public ResponseEntity<List<ChallengeListDto>> getChallengeList() {


        //시작도 안하고 종료도 안한 챌린지 목록 조회
        List<ChallengeListDto> list = challengeService.getChallengeList(false, false);

        if (list.isEmpty()) {
            return ResponseEntity.ok().build(); // 빈 응답 반환
        }

        return ResponseEntity.ok(list);
    }



    /* 전체목록에서
     * 챌린지 상세조회
     *
     * 참여버튼 참여여부에 따라 보여주기
     *
     * @date 2023.05.11
     * @author 양윤정
     * */

    @GetMapping("/{id}")
    public ResponseEntity<?> getChallengeDetail(@AuthenticationPrincipal CustomUserDetails member, @PathVariable long id) {

        try {
            // 챌린지 상세 정보 가져오기
            ChallengeDetailResponseDto challengeDetail = challengeService.getChallengeDetail(member.getMember(), id);

            if (challengeDetail == null) {
                return ResponseEntity.notFound().build(); // 해당 챌린지가 존재하지 않을 경우 404 응답 반환
            }


            return ResponseEntity.ok(challengeDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /*
     * 챌린지 참여
     *
     *
     *
     * @date 2023.05.12
     * @author 양윤정
     * */

    @GetMapping("/join/{id}")
    public ResponseEntity<?> joinChallenge(@AuthenticationPrincipal CustomUserDetails member, @PathVariable long id) {

        boolean success = challengeService.joinChallenge(member.getMember(), id);
        if (success) {
            return ResponseEntity.ok("챌린지 참여 성공");
        } else {
            return ResponseEntity.badRequest().body("챌린지 참여 실패");
        }
    }

    /*
     * 챌린지 참여 취소
     *
     *
     *
     * @date 2023.05.12
     * @author 양윤정
     * */

    @DeleteMapping("cancel/{id}")
    public ResponseEntity<?> cancelChallengeJoin(@AuthenticationPrincipal CustomUserDetails member, @PathVariable long id) {
        boolean result = challengeService.cancelChallenge(member.getMember(), id);

        if (result) {
            return ResponseEntity.ok().build(); // 챌린지 참여 취소 성공 시 200 응답 반환
        } else {
            return ResponseEntity.notFound().build(); // 해당 챌린지가 존재하지 않거나 참여 취소 실패 시 404 응답 반환
        }
    }


    /*
     * 챌린지 목록조회
     *
     *
     * 참여중인 챌린지들 보여주기
     *
     * @date 2023.05.12
     * @author 양윤정
     * */


    @GetMapping("/progress")
    public ResponseEntity<List<ChallengeListDto>> getChallengeInProgressList(@AuthenticationPrincipal CustomUserDetails member) {


        //시작 하고 종료 안한 챌린지 목록 조회
        List<ChallengeListDto> list = challengeService.getChallengeInProgressList(true, false, member.getMember());

        if (list.isEmpty()) {
            return ResponseEntity.ok().build(); // 빈 응답 반환
        }

        return ResponseEntity.ok(list);
    }



    /*
     * 챌린지 상세조회
     *
     * 참여중인 챌린지 상세 조회
     *
     * @date 2023.05.12
     * @author 양윤정
     * */

    @GetMapping("/progress/{id}")
    public ResponseEntity<?> getChallengeInProgressDetail(@AuthenticationPrincipal CustomUserDetails member, @PathVariable long id) {

        try {
            // 챌린지 상세 정보 가져오기
            ChallengeMemberDetailResponse challengeDetail = challengeService.getChallengeInProgressDetail(member.getMember(), id);

            if (challengeDetail == null) {
                return ResponseEntity.notFound().build(); // 해당 챌린지가 존재하지 않을 경우 404 응답 반환
            }


            return ResponseEntity.ok(challengeDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*
     * 챌린지 목록조회
     *
     *
     * 참여완료 챌린지들 보여주기
     *
     * @date 2023.05.12
     * @author 양윤정
     * */


    @GetMapping("/finish")
    public ResponseEntity<List<ChallengeListDto>> getChallengeIsFinishedList(@AuthenticationPrincipal CustomUserDetails member) {


        //종료한 챌린지 목록 조회
        List<ChallengeListDto> list = challengeService.getChallengeIsFinishedList(false, true, member.getMember());

        if (list.isEmpty()) {
            return ResponseEntity.ok().build(); // 빈 응답 반환
        }

        return ResponseEntity.ok(list);
    }

    /*
     * 챌린지 참여완료 처리
     *
     *
     * @date 2023.05.12
     * @author 양윤정
     * */
    @PutMapping("/progress/success/{id}")
    public ResponseEntity<String> setChallengeSuccess(@AuthenticationPrincipal CustomUserDetails member, @PathVariable long id) {
        challengeService.successChallenge(member.getMember(), id);
        return ResponseEntity.ok("챌린지 성공적 처리");
    }




    /*
     * 완료 챌린지 상세조회
     *
     * 참여중인 챌린지 상세 조회
     *
     * @date 2023.05.12
     * @author 양윤정
     * */

    @GetMapping("/finish/{id}")
    public ResponseEntity<?> getChallengeIsFinishedDetail(@AuthenticationPrincipal CustomUserDetails member, @PathVariable long id) {

        try {
            // 챌린지 상세 정보 가져오기
            ChallengeDetailResponseFinishedDto challengeDetail = challengeService.getChallengeFinsishedDetail(member.getMember(), id);

            if (challengeDetail == null) {
                return ResponseEntity.notFound().build(); // 해당 챌린지가 존재하지 않을 경우 404 응답 반환
            }


            return ResponseEntity.ok(challengeDetail);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
