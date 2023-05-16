package com.ssafy.moneykeeperbackend.circle.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.circle.entity.Circle;

@Repository
public interface CircleRepository extends JpaRepository<Circle, Long> {

	boolean existsByName(String name);

	Optional<Circle> findById(Long id);

	@Modifying
	void deleteById(Long id);
}
