package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.statistics.entity.*;
import com.ssafy.moneykeeperbackend.statistics.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenereateRecordService {
    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;
    private final MonthSpendingRecordRepository monthSpendingRecordRepository;
    private final MonthIncomeRecordRepository monthIncomeRecordRepository;
    private final MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;
    @Transactional
    public void initForNewMember(Member member) {

        LocalDate now = LocalDate.now();

        LocalDate ymonth = LocalDate.of(now.getYear(),now.getMonth(),1);
        generateRecordForMonth(member, ymonth);
    }

    public void generateRecordForMonth(Member member, LocalDate ymonth) {

        try { // do this for now change later

            MonthSpendingRecord msr =
                    MonthSpendingRecord.builder()
                            .ymonth(ymonth)
                            .member(member)
                            .amount(0)
                            .build();
            monthSpendingRecordRepository.save(msr);

            List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();

            for (MajorSpendingClassification msc : mscList) {
                MonthSpendingRecordByClass msrc =
                        MonthSpendingRecordByClass.builder()
                                .ymonth(ymonth)
                                .member(member)
                                .majorSpendingClass(msc)
                                .amount(0)
                                .build();
                monthSpendingRecordByClassRepository.save(msrc);
            }

            MonthIncomeRecord monthIncomeRecord =
                    MonthIncomeRecord.builder()
                            .month(ymonth)
                            .member(member)
                            .amount(0)
                            .build();
            monthIncomeRecordRepository.save(monthIncomeRecord);
        } catch (Exception e) {
            System.out.println("generate record for month");
        }
    }
}
