package com.ssafy.moneykeeperbackend.group.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
import com.ssafy.moneykeeperbackend.exception.auth.AuthExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.auth.AuthRuntimeException;
import com.ssafy.moneykeeperbackend.exception.group.GroupExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.group.GroupRuntimeException;
import com.ssafy.moneykeeperbackend.group.dto.request.GroupRequest;
import com.ssafy.moneykeeperbackend.group.dto.response.GroupMemberResponse;
import com.ssafy.moneykeeperbackend.group.dto.response.GroupResponse;
import com.ssafy.moneykeeperbackend.group.entity.Group;
import com.ssafy.moneykeeperbackend.group.entity.GroupRole;
import com.ssafy.moneykeeperbackend.group.entity.MemberGroup;
import com.ssafy.moneykeeperbackend.group.repository.GroupRepository;
import com.ssafy.moneykeeperbackend.group.repository.MemberGroupRepository;
import com.ssafy.moneykeeperbackend.group.service.GroupService;
import com.ssafy.moneykeeperbackend.member.dto.response.MemberResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

	private final GroupRepository groupRepository;

	private final MemberGroupRepository memberGroupRepository;

	private final MemberRepository memberRepository;

	private final SpendingService spendingService;

	/*
	 * 그룹 생성
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public GroupResponse addGroup(GroupRequest groupRequest, Member member) {

		if (existsGroupName(groupRequest.getName())) {
			throw new GroupRuntimeException(GroupExceptionEnum.EXISTS_GROUP_NAME);
		}

		Group group = groupRepository.saveAndFlush(Group.builder()
			.name(groupRequest.getName())
			.build());

		memberGroupRepository.saveAndFlush(
			MemberGroup.builder()
				.group(group)
				.member(member)
				.groupRole(GroupRole.GROUP_LEADER)
				.build()
		);

		List<Long> member_ids = groupRequest.getMember_ids();

		for (Long member_id : member_ids) {
			memberGroupRepository.saveAndFlush(
				MemberGroup.builder()
					.group(group)
					.member(memberRepository.findById(member_id).orElseThrow(() -> new AuthRuntimeException(
						AuthExceptionEnum.MEMBER_ID_NULL)))
					.groupRole(GroupRole.GROUP_MEMBER)
					.build()
			);
		}

		List<GroupMemberResponse> groupMemberResponses = new ArrayList<>();
		List<MemberGroup> resultMemberGroups = memberGroupRepository.findByGroup(group);

		for (MemberGroup memberGroup : resultMemberGroups) {
			groupMemberResponses.add(GroupMemberResponse.builder()
				.member_id(memberGroup.getMember().getId())
				.nickname(memberGroup.getMember().getNickname())
				.thisMonthTotalAmount(0)
				.build());
		}

		return GroupResponse.builder()
			.group_id(group.getId())
			.name(group.getName())
			.allMembers(groupMemberResponses)
			.build();
	}

	/*
	 * 그룹명 중복 체크
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	public boolean existsGroupName(String name) {
		return groupRepository.existsByName(name);
	}

	/*
	 * 그룹 정보 조회
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	public GroupResponse getGroupInfo(Long groupId, int year, int month) {
		Group group = groupRepository.findById(groupId)
			.orElseThrow(() -> new GroupRuntimeException(GroupExceptionEnum.GROUP_ID_NULL));

		List<MemberGroup> memberGroups = group.getMemberGroups();

		//전체 그룹 멤버들의 이번 달 소비 금액 가져오기
		List<GroupMemberResponse> groupMemberResponses = new ArrayList<>();

		for (MemberGroup memberGroup : memberGroups) {
			Member member = memberGroup.getMember();

			groupMemberResponses.add(GroupMemberResponse.builder()
				.member_id(member.getId())
				.email(member.getEmail())
				.nickname(member.getNickname())
				.thisMonthTotalAmount(spendingService.getMonthSpendingAmount(memberGroup.getMember(), year, month))
				.build());
		}

		Collections.sort(groupMemberResponses, Comparator.comparingInt(GroupMemberResponse::getThisMonthTotalAmount).reversed());

		List<GroupMemberResponse> top3Members = groupMemberResponses.stream()
			.limit(3)
			.collect(Collectors.toList());

		return GroupResponse.builder()
			.group_id(group.getId())
			.name(group.getName())
			.top3Members(top3Members)
			.allMembers(groupMemberResponses)
			.build();
	}
}
