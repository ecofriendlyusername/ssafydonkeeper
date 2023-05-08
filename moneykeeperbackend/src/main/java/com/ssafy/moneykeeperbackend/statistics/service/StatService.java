package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.dto.CompareWithRecentXDto;
import com.ssafy.moneykeeperbackend.statistics.dto.MonthIncomeRecordDto;
import com.ssafy.moneykeeperbackend.statistics.dto.MonthSpendingRecordDto;
import com.ssafy.moneykeeperbackend.statistics.dto.TotalAndComparedDto;
import com.ssafy.moneykeeperbackend.statistics.entity.*;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthIncomeRecordRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordByClassRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.SpendingGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatService {
    private final com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;
    private final SpendingGroupRepository spendingGroupRepository;
    private final MonthSpendingRecordByClassRepository MonthSpendingRecordByClassRepository;

    private final MonthSpendingRecordRepository monthSpendingRecordRepository;
    private final MemberRepository memberRepository;
    private final MonthIncomeRecordRepository monthIncomeRecordRepository;

    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;
    public List<CompareWithRecentXDto> compareWithRecentXMonths(int months, Long memberId) {
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
        List<CompareWithRecentXDto> li = new ArrayList<>();
        //        List<MonthSpendingRecordByClass> curMonthSpendingRecordList = monthSpendingRecordByClassRepository.findByMemberAndYmonth(member,curMonth);
        List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();

        for (MajorSpendingClassification msc : mscList) {
            System.out.println("msc.name : " + msc.getName() + ", memberId : " + member.getId() +", curMonth : " + curMonth);
            MonthSpendingRecordByClass cur = monthSpendingRecordByClassRepository.findByMemberAndYmonthAndMajorSpendingClass(member,curMonth,msc);
            List<MonthSpendingRecordByClass> lastX = monthSpendingRecordByClassRepository.findByMemberAndMajorSpendingClassAndYmonthBetween(member,msc,firstMonth,lastMonth);

            int lastXTotal = 0;

            for (MonthSpendingRecordByClass pastM : lastX) {
                lastXTotal += pastM.getAmount();
            }

            CompareWithRecentXDto cwrd = CompareWithRecentXDto.builder()
                    .thisMonth(cur.getAmount())
                    .recentXAvg(lastXTotal/months)
                    .category(msc.getName())
                    .build();

            li.add(cwrd);
        }



        return li;


//        double curTotalAmount = 0;
//
//        for (MonthSpendingRecordByClass mr : curMonthSpendingRecordList) {
//            MajorSpendingClassification majorSClass = mr.getMajorSpendingClass();
//            if (majorSClass == null) {
//                continue;
//                // ... ?
//            }
//            String className = majorSClass.getName();
//            if (!map.containsKey(className)) {
//                map.put(className,new double[3]);
//            }
//            map.get(majorSClass.getName())[0] += mr.getAmount();
//            curTotalAmount += mr.getAmount();
//        }
//
//        List<MonthSpendingRecordByClass> recentMonthsRecordList = MonthSpendingRecordByClassRepository.findByMemberAndYmonthBetween(member,firstMonth,lastMonth);
//
//        double totalSum = 0;
//
//        for (MonthSpendingRecordByClass MonthSpendingRecord : recentMonthsRecordList) {
//            MajorSpendingClassification majorSClass = MonthSpendingRecord.getMajorSpendingClass();
//            if (majorSClass == null) {
//                continue;
//                // ... ?
//            }
//            String classificationName = majorSClass.getName();
//            if (!map.containsKey(classificationName)) continue;
//            double amount = MonthSpendingRecord.getAmount();
//            map.get(classificationName)[1] += amount;
//            totalSum += amount;
//        }
//
//        double[] data = new double[3];
//        data[0] = curTotalAmount;
//        data[1] = totalSum / months;
//
//        map.put("total",data);
//
//        for (double[] dt : map.values()) {
//            dt[1] = dt[1] / months;
//            if (dt[0] == 0) {
//                dt[2] = -100;
//                continue;
//            }
//            if (dt[1] > dt[0]) { // 감소
//                double diff = dt[1] - dt[0]; // 감소량
//                dt[2] = -(diff / dt[1] * 100);
//            } else {
//                double diff = dt[0] - dt[1];
//                dt[2] = diff / dt[1] * 100;
//            }
//        }
//
//        return map;
    }

    private void updateSpendingGroupForAUser(Member member) {
        LocalDate now = LocalDate.now();
        LocalDate lastDayOfLastMonth = now.minusDays(1);
        LocalDate end = LocalDate.of(lastDayOfLastMonth.getYear(),lastDayOfLastMonth.getMonth(),1);
        LocalDate start = end.minusMonths(3);

        List<MonthSpendingRecordByClass> monthSpendingRecordList = monthSpendingRecordByClassRepository.findByMemberAndYmonthBetween(member,start,end);

        if (monthSpendingRecordList.size() == 0) {
            System.out.println("no month spending record available now");
            // ...
            return;
        }

        int totalSpending = 0;
        for (MonthSpendingRecordByClass msr : monthSpendingRecordList) {
            totalSpending += msr.getAmount();
        }

        List<SpendingGroup> spendingGroups = spendingGroupRepository.findAllByOrderByBelowAsc();

        if (spendingGroups.size() == 0) {
            System.out.println("no spending group for now");
            // ...
            return;
        }

        for (int i = 0; i < spendingGroups.size() - 1; i++) {
            SpendingGroup sg = spendingGroups.get(i);
            if (totalSpending < sg.getBelow()) {
                member.setSpendingGroup(sg);
                memberRepository.save(member);
                return;
            }
        }

        member.setSpendingGroup(spendingGroups.get(spendingGroups.size() - 1));
        memberRepository.save(member);
    }

    public TotalAndComparedDto compareWithUsers(int year, int month, long memberId) {
        LocalDate ymonth = LocalDate.of(year,month,1);

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if (!optionalMember.isPresent()) {
            System.out.println("no member with memberId : " + memberId);
            return null;
            // do something
        }

        Member member = optionalMember.get();

        Optional<MonthSpendingRecord> optionalMonthSpendingRecord = monthSpendingRecordRepository.findByMemberAndYmonth(member,ymonth);

        if (!optionalMonthSpendingRecord.isPresent()) {
            System.out.println("no spending record for member " + memberId + " , with ymonth " + ymonth);
            return null;
            // for now
        }

        MonthSpendingRecord msr = optionalMonthSpendingRecord.get();

        System.out.println(msr.getYmonth() + " " + msr.getAmount() + " " + msr.getGroupAvg());

        IncomeGroup incomeGroup = msr.getIncomeGroup();

        TotalAndComparedDto tcd = TotalAndComparedDto.builder()
                .total(msr.getAmount())
                .groupAvg(msr.getGroupAvg())
                .base(incomeGroup.getBase())
                .below(incomeGroup.getBelow())
                .build();

        return tcd;
    }

    public void uponMemberJoin(Member member) {
        LocalDate now = LocalDate.now();
        LocalDate ymonth = LocalDate.of(now.getYear(),now.getMonth(),1);
        buildMonthSpendingRecordForAUser(member,ymonth);
        buildMonthIncomeRecordForAUser(member,ymonth);
        buildMonthSpendingRecordByClassesForAUser(member,ymonth);
    }

    public void buildMonthSpendingRecordForAUser(Member member, LocalDate ymonth) {
        MonthSpendingRecord msr = MonthSpendingRecord.builder()
                .member(member)
                .amount(0)
                .ymonth(ymonth)
                .groupAvg(-1)
                .build();
        monthSpendingRecordRepository.save(msr);
    }

    public void buildMonthIncomeRecordForAUser(Member member, LocalDate ymonth) {
        MonthIncomeRecord mir = MonthIncomeRecord.builder()
                .member(member)
                .month(ymonth)
                .amount(0)
                .build();
        monthIncomeRecordRepository.save(mir);
    }

    public void buildMonthSpendingRecordByClassesForAUser(Member member, LocalDate month) {
        List<MajorSpendingClassification> mscs = majorSpendingClassificationRepository.findAll();

        for (MajorSpendingClassification msc : mscs) {
            MonthSpendingRecordByClass msrbc = MonthSpendingRecordByClass.builder()
                    .month(month)
                    .amount(0)
                    .majorSpendingClass(msc)
                    .member(member)
                    .build();
            monthSpendingRecordByClassRepository.save(msrbc);
        }
    }

    public MonthSpendingRecordDto getMonthSpending(int year, int month, Long memberId) {
        LocalDate ymonth = LocalDate.of(year,month,1);
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (!optionalMember.isPresent()) {
            // ...
            System.out.println("member iwth memberId : " + memberId + " doesn't exist");
            return null;
        }
        Optional<MonthSpendingRecord> optionalMSR = monthSpendingRecordRepository.findByMemberAndYmonth(optionalMember.get(),ymonth);

        if (!optionalMSR.isPresent()) {
            System.out.println("Month Spending Record for member : " + memberId + " doesn't exist");
            return null;
        }

        MonthSpendingRecord msr = optionalMSR.get();

        MonthSpendingRecordDto msrd = MonthSpendingRecordDto.builder()
                .groupAvg(msr.getGroupAvg())
                .amount(msr.getAmount()).build();
        return msrd;
    }

    public MonthIncomeRecordDto getMonthIncome(int year, int month, Long memberId) {
        LocalDate ymonth = LocalDate.of(year,month,1);
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (!optionalMember.isPresent()) {
            // ...
            System.out.println("member iwth memberId : " + memberId + " doesn't exist");
            return null;
        }
        Optional<MonthIncomeRecord> optionalMIR = monthIncomeRecordRepository.findByMemberAndYmonth(optionalMember.get(),ymonth);

        if (!optionalMIR.isPresent()) {
            System.out.println("Month Spending Record for member : " + memberId + " doesn't exist");
            return null;
        }

        MonthIncomeRecord mir = optionalMIR.get();

        MonthIncomeRecordDto mird = MonthIncomeRecordDto.builder()
                .amount(mir.getAmount()).build();
        return mird;
    }
}
