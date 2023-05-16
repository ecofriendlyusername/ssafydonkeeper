package com.ssafy.moneykeeperbackend.circle.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
import com.ssafy.moneykeeperbackend.exception.auth.AuthExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.auth.AuthRuntimeException;
import com.ssafy.moneykeeperbackend.exception.circle.CircleExxceptionEnum;
import com.ssafy.moneykeeperbackend.exception.circle.CircleRuntimeException;
import com.ssafy.moneykeeperbackend.circle.dto.request.CircleRequest;
import com.ssafy.moneykeeperbackend.circle.dto.response.CircleMemberResponse;
import com.ssafy.moneykeeperbackend.circle.dto.response.CircleResponse;
import com.ssafy.moneykeeperbackend.circle.entity.Circle;
import com.ssafy.moneykeeperbackend.circle.entity.CircleRole;
import com.ssafy.moneykeeperbackend.circle.entity.MemberCircle;
import com.ssafy.moneykeeperbackend.circle.repository.CircleRepository;
import com.ssafy.moneykeeperbackend.circle.repository.MemberCircleRepository;
import com.ssafy.moneykeeperbackend.circle.service.CircleService;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CircleServiceImpl implements CircleService {

	private final CircleRepository circleRepository;

	private final MemberCircleRepository memberCircleRepository;

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
	public CircleResponse addCircle(CircleRequest circleRequest, Member member) {

		if (existsCircleName(circleRequest.getName())) {
			throw new CircleRuntimeException(CircleExxceptionEnum.EXISTS_CIRCLE_NAME);
		}

		Circle circle = circleRepository.saveAndFlush(Circle.builder()
			.name(circleRequest.getName())
			.build());

		memberCircleRepository.saveAndFlush(
			MemberCircle.builder()
				.circle(circle)
				.member(member)
				.circleRole(CircleRole.CIRCLE_LEADER)
				.build()
		);

		List<Long> member_ids = circleRequest.getMember_ids();

		for (Long member_id : member_ids) {
			memberCircleRepository.saveAndFlush(
				MemberCircle.builder()
					.circle(circle)
					.member(memberRepository.findById(member_id).orElseThrow(() -> new AuthRuntimeException(
						AuthExceptionEnum.MEMBER_ID_NULL)))
					.circleRole(CircleRole.CIRCLE_MEMBER)
					.build()
			);
		}

		List<CircleMemberResponse> circleMemberRespons = new ArrayList<>();
		List<MemberCircle> resultMemberCircles = memberCircleRepository.findByCircle(circle);

		for (MemberCircle memberCircle : resultMemberCircles) {
			circleMemberRespons.add(CircleMemberResponse.builder()
				.member_id(memberCircle.getMember().getId())
				.nickname(memberCircle.getMember().getNickname())
				.circleRole(memberCircle.getCircleRole().toString())
				.thisMonthTotalAmount(0)
				.build());
		}

		return CircleResponse.builder()
			.circle_id(circle.getId())
			.leader_id(member.getId())
			.name(circle.getName())
			.allMembers(circleMemberRespons)
			.build();
	}

	/*
	 * 그룹명 중복 체크
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	public boolean existsCircleName(String name) {
		return circleRepository.existsByName(name);
	}

	/*
	 * 그룹 정보 조회
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@Override
	public CircleResponse getCircleInfo(Long circleId, int year, int month) {
		Circle circle = circleRepository.findById(circleId)
			.orElseThrow(() -> new CircleRuntimeException(CircleExxceptionEnum.CIRCLE_ID_NULL));

		List<MemberCircle> memberCircles = circle.getMemberCircles();

		//전체 그룹 멤버들의 이번 달 소비 금액 가져오기
		List<CircleMemberResponse> circleMemberResponse = new ArrayList<>();

		Member leader = null;
		for (MemberCircle memberCircle : memberCircles) {
			// 리더 확인
			if (memberCircle.getCircleRole() == CircleRole.CIRCLE_LEADER) {
				leader = memberCircle.getMember();
			}

			Member member = memberCircle.getMember();

			circleMemberResponse.add(CircleMemberResponse.builder()
				.member_id(member.getId())
				.email(member.getEmail())
				.nickname(member.getNickname())
				.circleRole(memberCircle.getCircleRole().toString())
				.thisMonthTotalAmount(spendingService.getMonthSpendingAmount(memberCircle.getMember(), year, month))
				.build());
		}

		Collections.sort(circleMemberResponse,
			Comparator.comparingInt(CircleMemberResponse::getThisMonthTotalAmount));

		List<CircleMemberResponse> top3Members = circleMemberResponse.stream()
			.limit(3)
			.collect(Collectors.toList());

		circleMemberResponse.removeAll(top3Members);

		return CircleResponse.builder()
			.circle_id(circle.getId())
			.leader_id(leader.getId())
			.name(circle.getName())
			.top3Members(top3Members)
			.allMembers(circleMemberResponse)
			.build();
	}

	/*
	 * 그룹 탈퇴
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public void deleteMemberCircle(Long circleId, Member member) {
		memberCircleRepository.deleteByMemberAndCircle(member, circleRepository.findById(circleId)
			.orElseThrow(() -> new CircleRuntimeException(CircleExxceptionEnum.CIRCLE_ID_NULL)));
	}

	/*
	 * 그룹 삭제
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public void deleteCircle(Long circleId, Member member) {
		MemberCircle memberCircle = memberCircleRepository.findByMemberAndCircle(member,
				circleRepository.findById(circleId)
					.orElseThrow(() -> new CircleRuntimeException(CircleExxceptionEnum.CIRCLE_ID_NULL)))
			.orElseThrow(() -> new CircleRuntimeException(CircleExxceptionEnum.CIRCLE_ID_AND_MEMBER_RELATION_NULL));

		if (memberCircle.getCircleRole() == CircleRole.CIRCLE_LEADER) {
			circleRepository.deleteById(circleId);
		} else {
			throw new CircleRuntimeException(CircleExxceptionEnum.MEMBER_IS_NOT_CIRCLE_LEADER);
		}
	}

	/*
	 * 로그인한 멤버가 가입된 그룹 전체 조회
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@Override
	public List<IdNameDTO> getAllMembersCircle(Member member) {
		return memberCircleRepository.findByMember(member).stream().map(memberCircle -> IdNameDTO.builder()
				.id(memberCircle.getCircle().getId())
				.name(memberCircle.getCircle().getName())
				.build())
			.collect(Collectors.toList());
	}

	/*
	 * 그룹 멤버 초대
	 *
	 * @date 2023.05.16
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public CircleResponse addMember(Long circleId, CircleRequest circleRequest, Member member) {
		List<Long> memberIds = circleRequest.getMember_ids();

		Circle circle = circleRepository.findById(circleId)
			.orElseThrow(() -> new CircleRuntimeException(CircleExxceptionEnum.CIRCLE_ID_NULL));

		List<MemberCircle> alreadyInvitedMembers = memberCircleRepository.findByCircle(circle);

		List<Member> membersToInvite = memberIds.stream()
			.filter(memberId -> !alreadyInvitedMembers.stream()
				.map(MemberCircle::getMember)
				.anyMatch(mem -> mem.getId().equals(memberId)))
			.map(memberId -> memberRepository.findById(memberId)
				.orElseThrow(() -> new AuthRuntimeException(AuthExceptionEnum.MEMBER_ID_NULL)))
			.collect(Collectors.toList());

		for (Member inviteMem : membersToInvite) {
			memberCircleRepository.saveAndFlush(MemberCircle.builder()
				.member(inviteMem)
				.circle(circle)
				.circleRole(CircleRole.CIRCLE_MEMBER)
				.build());
		}

		LocalDate currentDate = LocalDate.now();
		return getCircleInfo(circleId, currentDate.getYear(), currentDate.getMonthValue());
	}

	/*
	 * 그룹 이름 수정
	 *
	 * @date 2023.05.16
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public CircleResponse updateName(Long circleId, String name, Member member) {
		Circle circle = circleRepository.findById(circleId)
			.orElseThrow(() -> new CircleRuntimeException(CircleExxceptionEnum.CIRCLE_ID_NULL));

		if (existsCircleName(name)) {
			throw new CircleRuntimeException(CircleExxceptionEnum.EXISTS_CIRCLE_NAME);
		}

		circle.setName(name);
		circleRepository.save(circle);

		LocalDate currentDate = LocalDate.now();
		return getCircleInfo(circleId, currentDate.getYear(), currentDate.getMonthValue());
	}

}
