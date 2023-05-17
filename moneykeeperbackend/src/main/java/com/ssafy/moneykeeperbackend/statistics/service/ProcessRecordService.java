package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.Income;
import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingRepository;
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

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProcessRecordService {

    private final SpendingRepository spendingRepository;

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

    @Transactional
    public void processDeletedSpending(Long spendingId) {
        Optional<Spending> spendingO = spendingRepository.findById(spendingId);
        if (spendingO.isEmpty()) return;
        Spending spending = spendingO.get();
        SpendingClassification spendingClassification = spending.getSpendingClassification();
        if (spendingClassification == null) return;
        MajorSpendingClassification msc = spending.getSpendingClassification().getMajorSpendingClassification();
        if (msc == null) return;
        Member member = spending.getMember();
        LocalDate spendingDate = spending.getDate();
        LocalDate ld = LocalDate.of(spendingDate.getYear(),spendingDate.getMonth(),1);
        Optional<MonthSpendingRecord> msrO = monthSpendingRecordRepository.findByMemberAndYmonth(member,ld);
        if (msrO.isEmpty()) return;
        MonthSpendingRecord msr = msrO.get();
        MonthSpendingRecordByClass msrC = monthSpendingRecordByClassRepository.findByMemberAndYmonthAndMajorSpendingClass(member,ld,msc);

        msr.setAmount(msr.getAmount()-spending.getAmount());
        msrC.setAmount(msrC.getAmount()-spending.getAmount());

        monthSpendingRecordRepository.save(msr);
        monthSpendingRecordByClassRepository.save(msrC);
    }
    @Transactional
    public void processUpdatedSpending(Spending spending, Spending updated) {
        SpendingClassification spendingClassification = spending.getSpendingClassification();
        if (spendingClassification == null) return;
        MajorSpendingClassification msc = spending.getSpendingClassification().getMajorSpendingClassification();
        if (msc == null) return;
        Member member = spending.getMember();
        LocalDate spendingDate = spending.getDate();
        LocalDate ld = LocalDate.of(spendingDate.getYear(),spendingDate.getMonth(),1);
        LocalDate updatedSpendingDate = updated.getDate();
        LocalDate ldUpdated = LocalDate.of(updatedSpendingDate.getYear(),updatedSpendingDate.getMonth(),1);
        Optional<MonthSpendingRecord> msrO = monthSpendingRecordRepository.findByMemberAndYmonth(member,ld);
        if (msrO.isEmpty()) return;
        MonthSpendingRecord msr = msrO.get();
        MonthSpendingRecordByClass msrC = monthSpendingRecordByClassRepository.findByMemberAndYmonthAndMajorSpendingClass(member,ld,msc);


        Optional<MonthSpendingRecord> msrOUpdated = monthSpendingRecordRepository.findByMemberAndYmonth(member,ldUpdated);
        if (msrOUpdated.isEmpty()) return;
        MonthSpendingRecord msrUpdated = msrO.get();
        MonthSpendingRecordByClass msrCUpdated = monthSpendingRecordByClassRepository.findByMemberAndYmonthAndMajorSpendingClass(member,ldUpdated,msc);


        msr.setAmount(msr.getAmount()-spending.getAmount());
        msrC.setAmount(msrC.getAmount()-spending.getAmount());

        msrUpdated.setAmount(msr.getAmount()-updated.getAmount());
        msrCUpdated.setAmount(msrC.getAmount()-updated.getAmount());

        monthSpendingRecordRepository.save(msr);
        monthSpendingRecordByClassRepository.save(msrC);
    }
}
