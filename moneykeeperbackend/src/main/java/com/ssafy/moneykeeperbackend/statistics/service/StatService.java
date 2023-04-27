package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.record.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.record.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthRecordRepository;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
@RequiredArgsConstructor
public class StatService {

    SpendingClassificationRepository spendingClassificationRepository;

    private final MonthRecordRepository monthRecordRepository;

    private final MemberRepository memberRepository;
    public Map<String, double[]> compareWithRecentXMonths(int months, Long memberId) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate curMonth = LocalDate.of(localDate.getYear(),localDate.getMonth(),1);
        LocalDate firstMonth = curMonth.minusMonths(months);
        LocalDate lastMonth = curMonth.minusMonths(1);

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if (!optionalMember.isPresent()) {
            // .. ?
        }
        Member member = optionalMember.get();


        List<MonthRecord> curMonthRecordList = monthRecordRepository.findByMemberAndYmonth(member,curMonth);

        Map<String,double[]> map = new HashMap<>();


        double curTotalAmount = 0;

        for (MonthRecord mr : curMonthRecordList) {
            SpendingClassification spendingClassification = mr.getSpendingClassification();
            if (spendingClassification == null) {
                continue;
                // ... ?
            }
            String className = spendingClassification.getName();
            if (!map.containsKey(className)) {
                map.put(className,new double[3]);
            }
            map.get(spendingClassification.getName())[0] += mr.getAmount();
            curTotalAmount += mr.getAmount();
        }

        List<MonthRecord> recentMonthsRecordList = monthRecordRepository.findByMemberAndYmonthBetween(member,firstMonth,lastMonth);

        double totalSum = 0;

        for (MonthRecord monthRecord : recentMonthsRecordList) {
            SpendingClassification spendingClassification = monthRecord.getSpendingClassification();
            if (spendingClassification == null) {
                continue;
                // ... ?
            }
            String classificationName = spendingClassification.getName();
            if (!map.containsKey(classificationName)) continue;
            double amount = monthRecord.getAmount();
            map.get(classificationName)[1] += amount;
            totalSum += amount;
        }

        double[] data = new double[3];
        data[0] = curTotalAmount;
        data[1] = totalSum / months;

        map.put("total",data);

        for (double[] dt : map.values()) {
            dt[1] = dt[1] / months;
            if (dt[0] == 0) {
                dt[2] = -100;
                continue;
            }
            if (dt[1] > dt[0]) { // 감소
                double diff = dt[1] - dt[0]; // 감소량
                dt[2] = -(diff / dt[1] * 100);
            } else {
                double diff = dt[0] - dt[1];
                dt[2] = diff / dt[1] * 100;
            }
        }

        return map;
    }
}
