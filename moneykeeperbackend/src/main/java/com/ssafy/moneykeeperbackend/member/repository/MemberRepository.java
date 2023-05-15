package com.ssafy.moneykeeperbackend.member.repository;

import java.util.List;
import java.util.Optional;

import com.ssafy.moneykeeperbackend.statistics.entity.IncomeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

    Member findByNicknameAndEmail(String nickname, String email);

    List<Member> findByIncomeGroup(IncomeGroup incomeGroup);

    Member findByNickname(String rs);

	List<Member> findByEmailLike(String email);
}
