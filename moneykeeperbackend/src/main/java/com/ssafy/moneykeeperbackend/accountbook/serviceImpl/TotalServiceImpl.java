package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.moneykeeperbackend.accountbook.dto.response.DateTotalAmountResponse;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.MonthTatalAmountResponse;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.TotalAmountResponse;
import com.ssafy.moneykeeperbackend.accountbook.repository.IncomeRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.IncomeService;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
import com.ssafy.moneykeeperbackend.accountbook.service.TotalService;
import com.ssafy.moneykeeperbackend.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TotalServiceImpl implements TotalService {

	private final IncomeRepository incomeRepository;

	private final SpendingRepository spendingRepository;

	private final SpendingService spendingService;

	private final IncomeService incomeService;

	/*
	 * 특정 달의 매일 소비/ 수입 금액과 총 소비/ 수입 금액 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Override
	public MonthTatalAmountResponse getMonthTotalAmmount(Member member, int year, int month) {

		List<DateTotalAmountResponse> dateTotalAmountResponses = new ArrayList<>();

		YearMonth yearMonth = YearMonth.of(year, month);
		int daysInMonth = yearMonth.lengthOfMonth();

		for (int day = 1; day <= daysInMonth; day++) {
			LocalDate date = LocalDate.of(year, month, day);
			DateTotalAmountResponse dateTotalAmountResponse = getDateTotalAmmount(member, date);
			dateTotalAmountResponses.add(dateTotalAmountResponse);
		}

		return MonthTatalAmountResponse.builder()
			.month(String.format("%d-%02d", year, month))
			.details(dateTotalAmountResponses)
			.total(TotalAmountResponse.builder()
				.totalIncomeAmount(incomeService.getMonthIncomeAmount(member, year, month))
				.totalSpendingAmount(spendingService.getMonthSpendingAmount(member, year, month))
				.build())
			.build();
	}

	/*
	 * 특정 날의 소비/ 수입 금액 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Override
	public DateTotalAmountResponse getDateTotalAmmount(Member member, LocalDate date) {
		int incomeAmount = incomeRepository.getTotalIncomeOnDateForMember(date, member);
		int spendingAmount = spendingRepository.getTotalSpendingOnDateForMember(date, member);

		return DateTotalAmountResponse.builder()
			.date(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.incomeAmount(incomeAmount)
			.spendingAmount(spendingAmount)
			.build();
	}
}
