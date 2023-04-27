package com.ssafy.moneykeeperbackend.record.repository;

import com.ssafy.moneykeeperbackend.record.entity.Asset;
import com.ssafy.moneykeeperbackend.record.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, Long> {
}
