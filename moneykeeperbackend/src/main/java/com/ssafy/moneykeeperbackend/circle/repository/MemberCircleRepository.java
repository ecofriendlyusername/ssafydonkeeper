package com.ssafy.moneykeeperbackend.circle.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.circle.entity.Circle;
import com.ssafy.moneykeeperbackend.circle.entity.MemberCircle;
import com.ssafy.moneykeeperbackend.member.entity.Member;

@Repository
public interface MemberCircleRepository extends JpaRepository<MemberCircle, Long> {

	List<MemberCircle> findByCircle(Circle circle);

	List<MemberCircle> findByMember(Member member);

	Optional<MemberCircle> findByMemberAndCircle(Member member, Circle circle);

	@Modifying
	void deleteByMemberAndCircle(Member member, Circle circle);
}
