package com.ssafy.moneykeeperbackend.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

    Member findByNicknameAndEmail(String nickname, String email);
}
