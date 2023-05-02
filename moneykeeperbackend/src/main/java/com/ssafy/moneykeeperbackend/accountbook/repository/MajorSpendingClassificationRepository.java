package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;

@Repository
public interface MajorSpendingClassificationRepository extends JpaRepository<MajorSpendingClassification, Long> {

	List<MajorSpendingClassification> findAll();

	MajorSpendingClassification findByName(String sclass);
}
