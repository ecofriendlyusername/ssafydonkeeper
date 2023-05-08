package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.member.entity.Member;

@Repository
public interface SpendingClassificationRepository extends JpaRepository<SpendingClassification, Long> {

	Optional<SpendingClassification> findById(Long id);

}
