package com.ssafy.moneykeeperbackend.challenge.service;

import com.ssafy.moneykeeperbackend.challenge.dto.request.ChallengeRequestDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeDetailResponseDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeDetailResponseFinishedDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeListDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeMemberDetailResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;

import java.util.List;

public interface ChallengeService {
    void addChallenge(ChallengeRequestDto challengeRequest)throws Exception;

    List<ChallengeListDto> getChallengeList(boolean inProgress, boolean isFinished);

    ChallengeDetailResponseDto getChallengeDetail(Member member, long id);

    boolean joinChallenge(Member member, long id);

    boolean cancelChallenge(Member member, long id);

    List<ChallengeListDto> getChallengeInProgressList(boolean inProgress, boolean isFinished, Member member);

    ChallengeMemberDetailResponse getChallengeInProgressDetail(Member member, long id);

    void successChallenge(Member member, long id);

    ChallengeDetailResponseFinishedDto getChallengeFinsishedDetail(Member member, long id);
}
