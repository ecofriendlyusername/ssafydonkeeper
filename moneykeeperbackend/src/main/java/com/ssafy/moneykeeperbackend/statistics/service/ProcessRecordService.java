package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.Income;
import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.exception.statistics.FailedToGenerateRecordException;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthIncomeRecord;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecord;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecordByClass;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthIncomeRecordRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordByClassRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProcessRecordService {

    private final StatService statService;

    private final MonthSpendingRecordRepository monthSpendingRecordRepository;

    private final MonthIncomeRecordRepository monthIncomeRecordRepository;

    private final SpendingClassificationRepository spendingClassificationRepository;

    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;

    private final MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;

    public void processNewSpending(Spending spending, Member member) {
        LocalDate date = spending.getDate();
        LocalDate ymonth = LocalDate.of(date.getYear(),date.getMonth(),1);

        Optional<SpendingClassification> optionalSC = spendingClassificationRepository.findById(spending.getSpendingClassification().getId());

        if (!optionalSC.isPresent()) {
            throw new FailedToGenerateRecordException();
        }

        SpendingClassification sc = optionalSC.get();

        MajorSpendingClassification msc = sc.getMajorSpendingClassification();

        if (msc == null) {
            throw new FailedToGenerateRecordException();
        }

        Optional<MonthSpendingRecord> msrOptional = monthSpendingRecordRepository.findByMemberAndYmonth(member,ymonth);

        MonthSpendingRecord msr = msrOptional.isEmpty() ? statService.buildMonthSpendingRecordForAUser(member,ymonth) : msrOptional.get();

        MonthSpendingRecordByClass msrc = monthSpendingRecordByClassRepository.findByMemberAndYmonthAndMajorSpendingClass(member,ymonth,msc);

        if (msrc == null) {
            statService.buildMonthSpendingRecordByClassesForAUser(member,ymonth);
        }

        msrc = monthSpendingRecordByClassRepository.findByMemberAndYmonthAndMajorSpendingClass(member,ymonth,msc);

        if (msrc == null) {
            throw new FailedToGenerateRecordException();
        }

        msr.setAmount(msr.getAmount()+spending.getAmount());
        msrc.setAmount(msrc.getAmount()+spending.getAmount());
    }

    public void processNewIncome(Income income, Member member) {
        LocalDate date = income.getDate();
        LocalDate ymonth = LocalDate.of(date.getYear(),date.getMonth(),1);

        Optional<MonthIncomeRecord> optionalMir = monthIncomeRecordRepository.findByMemberAndYmonth(member,ymonth);

        if (optionalMir.isEmpty()) {
            statService.buildMonthIncomeRecordForAUser(member,ymonth);
        }

        optionalMir = monthIncomeRecordRepository.findByMemberAndYmonth(member,ymonth);

        if (optionalMir.isEmpty()) {
            throw new NoSuchElementException();
        }

        MonthIncomeRecord mir = optionalMir.get();

        mir.setAmount(mir.getAmount()+income.getAmount());
    }
}
