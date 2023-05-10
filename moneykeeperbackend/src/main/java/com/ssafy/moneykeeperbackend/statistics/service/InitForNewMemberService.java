package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthIncomeRecord;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecord;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecordByClass;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthIncomeRecordRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordByClassRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InitForNewMemberService {
    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;
    private final MonthSpendingRecordRepository monthSpendingRecordRepository;
    private final MonthIncomeRecordRepository monthIncomeRecordRepository;
    private final MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;
    @Transactional
    public void initForNewMember(Member member) {
        try { // do this for now change later
            LocalDate now = LocalDate.now();

            LocalDate ymonth = LocalDate.of(now.getYear(),now.getMonth(),1);

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
}
