package com.ssafy.moneykeeperbackend.accountbook.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.moneykeeperbackend.accountbook.dto.response.DateTotalAmountResponse;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.IncomeResponse;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.MonthTatalAmountResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface TotalService {

	MonthTatalAmountResponse getMonthTotalAmmount(Member member, int year, int month);

	DateTotalAmountResponse getDateTotalAmmount(Member member, LocalDate date);
}
