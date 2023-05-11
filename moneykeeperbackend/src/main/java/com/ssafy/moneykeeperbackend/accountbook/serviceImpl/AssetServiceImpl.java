package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.accountbook.entity.Asset;
import com.ssafy.moneykeeperbackend.accountbook.entity.IncomeClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.AssetRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.AssetService;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookRuntimeException;
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
	public List<IdNameDTO> getAllAsset(Member member) {
		return assetRepository.findByMember(member).stream().map(asset -> IdNameDTO.builder()
				.id(asset.getId())
				.name(asset.getName())
				.build())
			.collect(Collectors.toList());
	}

	/*
	 * 자산 분류 생성
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public IdNameDTO addAsset(Member member, IdNameDTO idNameDTO) {

		Asset asset = assetRepository.saveAndFlush(
			Asset.builder()
				.member(member)
				.total_account(Long.valueOf("0"))
				.name(idNameDTO.getName())
				.build()
		);

		return IdNameDTO.builder()
			.id(asset.getId())
			.name(asset.getName())
			.build();
	}

	/*
	 * 수입 분류 수정
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public IdNameDTO updateAsset(Member member, IdNameDTO idNameDTO, Long assetId) {

		Asset asset = assetRepository.findById(assetId)
			.orElseThrow(() -> new AccountBookRuntimeException(AccountBookExceptionEnum.ASSET_ID_NULL));

		if (idNameDTO.getName() != null && idNameDTO.getName() != asset.getName()) {
			asset.setName(idNameDTO.getName());
		}

		Asset resultAsset = assetRepository.save(asset);

		return IdNameDTO.builder()
			.id(resultAsset.getId())
			.name(resultAsset.getName())
			.build();
	}

	/*
	 * 특정 수입 분류 삭제
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public void deleteAsset(Long assetId) {
		assetRepository.deleteById(assetId);
	}
}
