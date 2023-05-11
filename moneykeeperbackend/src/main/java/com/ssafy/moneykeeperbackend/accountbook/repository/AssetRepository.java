package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.Asset;
import com.ssafy.moneykeeperbackend.member.entity.Member;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

	Optional<Asset> findById(Long id);

	List<Asset> findByMember(Member member);
}
