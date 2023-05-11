package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameClassificationDTO;
import com.ssafy.moneykeeperbackend.accountbook.repository.AssetRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.AssetService;
import com.ssafy.moneykeeperbackend.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

	private final AssetRepository assetRepository;

	/*
	 * 자산 분류 전체 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	public List<IdNameClassificationDTO> getAllAsset(Member member) {
		return assetRepository.findByMember(member).stream().map(asset -> IdNameClassificationDTO.builder()
			.id(asset.getId())
			.name(asset.getName())
			.build())
			.collect(Collectors.toList());
	}
}
