package com.ssafy.moneykeeperbackend.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.group.entity.Group;
import com.ssafy.moneykeeperbackend.group.entity.MemberGroup;
import com.ssafy.moneykeeperbackend.member.entity.Member;

@Repository
public interface MemberGroupRepository extends JpaRepository<MemberGroup, Long> {

	List<MemberGroup> findByGroup(Group group);

	Optional<MemberGroup> findByMemberAndGroup(Member member, Group group);

	@Modifying
	void deleteByMemberAndGroup(Member member, Group group);
}
