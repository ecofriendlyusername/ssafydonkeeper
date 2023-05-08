package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.dto.request.IncomeRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingClassificationRepository;
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
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProcessSpendingService {

    private final MonthSpendingRecordRepository monthSpendingRecordRepository;

    private final MonthIncomeRecordRepository monthIncomeRecordRepository;

    private final SpendingClassificationRepository spendingClassificationRepository;

    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;

    private final MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;

    public void processNewSpending(SpendingRequest spendingRequest, Member member) {
        LocalDate now = LocalDate.now();
        LocalDate ymonth = LocalDate.of(now.getYear(),now.getMonth(),1);

        Optional<SpendingClassification> optionalSC = spendingClassificationRepository.findById(spendingRequest.getSpendingClassificationId());

        if (!optionalSC.isPresent()) {
            System.out.println("spending classification with id " + spendingRequest.getSpendingClassificationId() + " doesn't exist");
            return;
        }

        SpendingClassification sc = optionalSC.get();

        MajorSpendingClassification msc = sc.getMajorSpendingClassification();

        if (msc == null) {
            System.out.println("major spending classification doesn't exist for spending classification with id " + spendingRequest.getSpendingClassificationId());
            return;
        }

        Optional<MonthSpendingRecord> msrOptional = monthSpendingRecordRepository.findByMemberAndYmonth(member,ymonth);
        if (!msrOptional.isPresent()) {
            // throw new NoSuchRecordException();
            System.out.println("Month Spending Record doesn't exist for member with memberId " + member.getId() + ", and ymonth " + ymonth);
            return;
        }

        MonthSpendingRecord msr = msrOptional.get();

        MonthSpendingRecordByClass msrc = monthSpendingRecordByClassRepository.findByMemberAndYmonthAndMajorSpendingClass(member,ymonth,msc);

        if (msrc == null) {
            System.out.println("MonthSpendingRecord doesn't exist for member with memberId " + member.getId() + " and ymonth : " + ymonth + " and major spending class id : " + msc.getId() + ", name : " + msc.getName());
            return;
        }

        msr.setAmount(msr.getAmount()+spendingRequest.getAmount());
        msrc.setAmount(msrc.getAmount()+spendingRequest.getAmount());
    }

    public void processNewIncome(IncomeRequest incomeRequest, Member member) {
        return;
        //        LocalDate now = LocalDate.now();
//        LocalDate ymonth = LocalDate.of(now.getYear(),now.getMonth(),1);
//
//        Optional<MonthIncomeRecord> mirOptional = monthIncomeRecordRepository.findByMemberAndYmonth(member,ymonth);
//
//        if (!mirOptional.isPresent()) {
//            // throw new NoSuchRecordException();
//            System.out.println("Month Spending Record doesn't exist for member with memberId " + member.getId() + ", and ymonth " + ymonth);
//            return;
//        }

//        MonthIncomeRecord msr = mirOptional.get();
//
//        Optional<MonthIncomeRecord> optionalMir = monthIncomeRecordRepository.findByMemberAndYmonth(member,ymonth);
//
//        if (optionalMir == null) {
//            System.out.println("MonthSpendingRecord doesn't exist for member with memberId " + member.getId() + " and ymonth : " + ymonth + ", name : ");
//            return;
//        }
//
//        MonthIncomeRecord mir = optionalMir.get();
//
//        mir.setAmount(mir.getAmount()+incomeRequest.getAmount());
    }
}
