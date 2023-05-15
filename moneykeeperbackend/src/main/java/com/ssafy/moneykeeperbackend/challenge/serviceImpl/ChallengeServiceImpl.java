package com.ssafy.moneykeeperbackend.challenge.serviceImpl;

import com.ssafy.moneykeeperbackend.challenge.dto.request.ChallengeRequestDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeDetailResponseDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeDetailResponseFinishedDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeListDto;
import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeMemberDetailResponse;
import com.ssafy.moneykeeperbackend.challenge.entity.Challenge;
import com.ssafy.moneykeeperbackend.challenge.entity.ChallengeMember;
import com.ssafy.moneykeeperbackend.challenge.repository.ChallengeMemberRepository;
import com.ssafy.moneykeeperbackend.challenge.repository.ChallengeRepository;
import com.ssafy.moneykeeperbackend.challenge.service.ChallengeService;
import com.ssafy.moneykeeperbackend.exception.challenge.ChallengeException;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMemberRepository challengeMemberRepository;
    public void addChallenge(ChallengeRequestDto challengeRequest)throws Exception{

        Challenge challenge = new Challenge();

        challenge.setName(challengeRequest.getName());
        challenge.setContent(challengeRequest.getContent());
        challenge.setStartDate(challengeRequest.getStartDate());
        challenge.setEndDate(challengeRequest.getEndDate());

        try {
            challengeRepository.save(challenge);
        } catch (Exception e) {
            throw new ChallengeException("챌린지 추가 실패", e);
        }
    }

    @Override
    public List<ChallengeListDto> getChallengeList(boolean inProgress, boolean isFinished) {
        List<Challenge> challenges = challengeRepository.findAllByInProgressAndIsFinished(inProgress, isFinished);
        List<ChallengeListDto> challengeListDtos = new ArrayList<>();

        for (Challenge challenge : challenges) {
            ChallengeListDto challengeListDto = new ChallengeListDto();
            challengeListDto.setId(challenge.getId());
            challengeListDto.setName(challenge.getName());
            challengeListDtos.add(challengeListDto);
        }

        return challengeListDtos;
    }

    @Override
    public ChallengeDetailResponseDto getChallengeDetail(Member member, long id) {
        Challenge challenge = challengeRepository.findById(id);
        if (challenge == null) {
            return null; // 해당 챌린지가 존재하지 않을 경우 null 반환
        }

        int participant = challengeMemberRepository.countByMemberAndId(member, id);
        boolean participantValue = participant == 1; // 참여 여부 판단

        ChallengeDetailResponseDto challengeDetailDto = new ChallengeDetailResponseDto();
        challengeDetailDto.setName(challenge.getName());
        challengeDetailDto.setContent(challenge.getContent());
        challengeDetailDto.setStartDate(challenge.getStartDate());
        challengeDetailDto.setEndDate(challenge.getEndDate());
        challengeDetailDto.setParticipant(participantValue);
        challengeDetailDto.setParticipantCount(challengeRepository.countById(id));
        return challengeDetailDto;
    }

    @Override
    public boolean joinChallenge(Member member, long id) {
        Challenge challenge = challengeRepository.findById(id);
        boolean result;
        if (challenge == null) {
            result = false;
        } else {
            ChallengeMember join = new ChallengeMember();
            join.setChallenge(challenge);
            join.setMember(member);
            result = challengeMemberRepository.save(join) != null;
        }
        return result;
    }

    @Override
    public boolean cancelChallenge(Member member, long id) {
        boolean result = false;
        ChallengeMember challengeMember =  challengeMemberRepository.findByChallengeIdAndMemberId(id, member.getId());

        if ( member.getId() == challengeMember.getMember().getId()) {
            challengeMemberRepository.deleteById(challengeMember.getId());
            result = true;
        }
        return result;
    }

    @Override
    public List<ChallengeListDto> getChallengeInProgressList(boolean inProgress, boolean isFinished, Member member) {
        LocalDate today = LocalDate.now();

        List<ChallengeListDto> challengeList = challengeMemberRepository.findChallengeNameAndMemberIdByMemberIdAndInProgressAndIsFinished(member.getId(), inProgress, isFinished);


        return challengeList;
    }

    @Override
    public ChallengeMemberDetailResponse getChallengeInProgressDetail(Member member, long id) {

        LocalDate today = LocalDate.now();  // 오늘 날짜

        ChallengeMemberDetailResponse result = new ChallengeMemberDetailResponse();
        ChallengeMember challengeMember = challengeMemberRepository.findById(id);
        Challenge challenge = challengeRepository.findById(challengeMember.getChallenge().getId());

        int duration = (int) ChronoUnit.DAYS.between(today, challenge.getEndDate()); // 남은 날짜
        int daysPassed = (int) (ChronoUnit.DAYS.between(challenge.getStartDate(), today) + 1); // 얼마나 지났는가

        String logs = challengeMember.getLogs();
        if (logs.length() < daysPassed - 2) {
            int zerosToAdd = daysPassed - 2 - logs.length();
            for (int i = 0; i < zerosToAdd; i++) {
                logs = logs + "0";
            }
        }


        if(duration<0){
           challenge.setInProgress(false);
           challenge.setFinished(true);
           challengeRepository.save(challenge);
        }


        result.setLogs(logs);
        // DB에 업데이트된 로그 저장
        challengeMember.setLogs(logs);


        challengeMemberRepository.save(challengeMember);

        result.setName(challenge.getName());
        result.setContent(challenge.getContent());
        result.setStartDate(challenge.getStartDate());
        result.setEndDate(challenge.getEndDate());
        result.setRemainingDuration(duration);


        return result;
    }


    @Override
    public void successChallenge(Member member, long id) {
        LocalDate today = LocalDate.now();  // 오늘 날짜

        ChallengeMember challengeMember = challengeMemberRepository.findById(id);
        Challenge challenge = challengeRepository.findById(challengeMember.getChallenge().getId());

        int duration = (int) ChronoUnit.DAYS.between(today, challenge.getEndDate()); // 남은 날짜
        int daysPassed = (int) (ChronoUnit.DAYS.between(challenge.getStartDate(), today) + 1); // 얼마나 지났는가
        int totalDate = (int) (ChronoUnit.DAYS.between(challenge.getStartDate(), challenge.getEndDate()) + 1); // 전체 날짜

        String logs = challengeMember.getLogs();

        logs = logs + "1"; // 로그에 1 추가

        long oneCount = logs.chars().filter(ch -> ch == '1').count(); // 1의 개수 세기

        if (oneCount >= totalDate * 0.8 && !challengeMember.isSuccess()) {
            challengeMember.setSuccess(true); // 성공으로 설정
        }

        challengeMember.setLogs(logs); // 업데이트된 로그 설정
        challengeMemberRepository.save(challengeMember); // DB에 저장
    }

    @Override
    public ChallengeDetailResponseFinishedDto getChallengeFinsishedDetail(Member member, long id) {

        ChallengeDetailResponseFinishedDto result = new ChallengeDetailResponseFinishedDto();
        ChallengeMember challengeMember = challengeMemberRepository.findById(id);
        Challenge challenge = challengeMember.getChallenge();
        result.setName(challenge.getName());
        result.setSuccess(challengeMember.isSuccess());
        result.setLogs(challengeMember.getLogs());
        result.setStartDate(challenge.getStartDate());
        result.setEndDate(challenge.getEndDate());


        return result;
    }
}
