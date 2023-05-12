package com.ssafy.moneykeeperbackend.challenge.repository;

import com.ssafy.moneykeeperbackend.challenge.dto.resopnse.ChallengeListDto;
import com.ssafy.moneykeeperbackend.challenge.entity.ChallengeMember;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeMemberRepository extends JpaRepository<ChallengeMember, Long> {
    int countByChallengeId(long id);



    ChallengeMember findById(long id);
    int countByMemberAndId(Member member, long id);


    ChallengeMember findByChallengeIdAndMemberId(long id, Long id1);

    @Query("SELECT c.name, cm.id FROM ChallengeMember cm JOIN cm.challenge c WHERE cm.member.id = :memberId AND c.inProgress = :inProgress AND c.isFinished = :isFinished")
    List<ChallengeListDto> findChallengeNameAndMemberIdByMemberIdAndInProgressAndIsFinished(@Param("memberId") long memberId, @Param("inProgress") boolean inProgress, @Param("isFinished") boolean isFinished);
}
