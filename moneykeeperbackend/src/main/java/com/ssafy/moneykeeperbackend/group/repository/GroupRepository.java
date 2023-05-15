package com.ssafy.moneykeeperbackend.group.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.group.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

	boolean existsByName(String name);

	Optional<Group> findById(Long id);
}
