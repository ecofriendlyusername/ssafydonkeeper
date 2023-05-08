package com.ssafy.moneykeeperbackend.statistics.repository;

import com.ssafy.moneykeeperbackend.statistics.entity.SpendingGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpendingGroupRepository extends JpaRepository<SpendingGroup, Long> {
    List<SpendingGroup> findAllByOrderByBelowAsc();
}
