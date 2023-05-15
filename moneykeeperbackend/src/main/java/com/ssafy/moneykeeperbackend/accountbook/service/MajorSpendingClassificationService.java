package com.ssafy.moneykeeperbackend.accountbook.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;

public interface MajorSpendingClassificationService {

	List<IdNameDTO> getAllMajorSpendingClassification();
}
