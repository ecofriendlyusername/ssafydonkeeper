package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;

@Repository
public interface MajorSpendingClassificationRepository extends JpaRepository<MajorSpendingClassification, Long> {

	List<MajorSpendingClassification> findAll();

	Optional<MajorSpendingClassification> findById(Long id);

    boolean existsByName(String s);
}
