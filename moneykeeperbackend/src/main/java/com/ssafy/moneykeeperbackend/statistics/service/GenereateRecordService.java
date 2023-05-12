package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.statistics.entity.*;
import com.ssafy.moneykeeperbackend.statistics.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenereateRecordService {

    private final UpdateDataService updateDataService;
    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;
    private final MonthSpendingRecordRepository monthSpendingRecordRepository;
    private final MonthIncomeRecordRepository monthIncomeRecordRepository;

    private final GroupSpendingRepository groupSpendingRepository;

    private final IncomeGroupRepository incomeGroupRepository;
    private final MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;
    @Transactional
    public void initForNewMember(Member member) {

        LocalDate now = LocalDate.now();

        LocalDate ymonth = LocalDate.of(now.getYear(),now.getMonth(),1);
        LocalDate lastMonth = ymonth.minusMonths(1);
        List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();
        List<IncomeGroup> igList = incomeGroupRepository.findAll();
//        updateDataService.generateGroupSpending(lastMonth,mscList,igList);
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
            // for now
            System.out.println("error in init for new member");
        }
    }

    public void generateGroupSpending(LocalDate ymonth) {
        List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();
        List<IncomeGroup> igList = incomeGroupRepository.findAll();

        for (MajorSpendingClassification msc : mscList) {
            for (IncomeGroup ig : igList) {
                GroupSpending groupSpending = GroupSpending.builder()
                        .incomeGroup(ig)
                        .majorSpendingClass(msc)
                        .total(0)
                        .months(0)
                        .ymonth(ymonth)
                        .build();
                groupSpendingRepository.saveAndFlush(groupSpending);
            }
        }
    }
}
