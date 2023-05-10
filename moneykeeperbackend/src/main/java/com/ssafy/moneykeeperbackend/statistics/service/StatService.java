package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.dto.*;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthIncomeRecord;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecord;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecordByClass;
import com.ssafy.moneykeeperbackend.statistics.entity.SpendingGroup;
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
    public List<CompareWithRecentXDto> compareWithRecentXMonths(int months, Member member) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate curMonth = LocalDate.of(localDate.getYear(),localDate.getMonth(),1);
        LocalDate firstMonth = curMonth.minusMonths(months);
        LocalDate lastMonth = curMonth.minusMonths(1);

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

    public TotalAndComparedDto compareWithUsers(int year, int month, Member member) {
        LocalDate ymonth = LocalDate.of(year,month,1);

        Optional<MonthSpendingRecord> optionalMonthSpendingRecord = monthSpendingRecordRepository.findByMemberAndYmonth(member,ymonth);

        if (!optionalMonthSpendingRecord.isPresent()) {
            System.out.println("no spending record for member " + member.getId() + " , with ymonth " + ymonth);
            return null;
            // for now
        }

        MonthSpendingRecord msr = optionalMonthSpendingRecord.get();

        System.out.println(msr.getYmonth() + " " + msr.getAmount() + " " + msr.getGroupAvg());

        TotalAndComparedDto tcd = TotalAndComparedDto.builder()
                .total(msr.getAmount())
                .groupAvg(msr.getGroupAvg())
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

    public MonthSpendingRecord buildMonthSpendingRecordForAUser(Member member, LocalDate ymonth) {
        MonthSpendingRecord msr = MonthSpendingRecord.builder()
                .member(member)
                .amount(0)
                .ymonth(ymonth)
                .groupAvg(-1)
                .build();
        return monthSpendingRecordRepository.save(msr);
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
                    .ymonth(month)
                    .amount(0)
                    .majorSpendingClass(msc)
                    .member(member)
                    .build();
            monthSpendingRecordByClassRepository.save(msrbc);
        }
    }

    public MonthSpendingRecordDto getMonthSpending(int year, int month, Member member) {
        LocalDate ymonth = LocalDate.of(year,month,1);
        Optional<MonthSpendingRecord> optionalMSR = monthSpendingRecordRepository.findByMemberAndYmonth(member,ymonth);

        if (!optionalMSR.isPresent()) {
            System.out.println("Month Spending Record for member : " + member.getId() + " doesn't exist");
            return null;
        }

        MonthSpendingRecord msr = optionalMSR.get();

        MonthSpendingRecordDto msrd = MonthSpendingRecordDto.builder()
                .groupAvg(msr.getGroupAvg())
                .amount(msr.getAmount()).build();
        return msrd;
    }

    public HashMap<String,Integer> getThreeMonthSpendingAvgByClass(Member member, LocalDate firstMonth, LocalDate lastMonth) {
        // cache this, update when month doesn't match

        List<MonthSpendingRecordByClass> msrcList = monthSpendingRecordByClassRepository.findByMemberAndYmonthBetween(member,firstMonth,lastMonth);

        int mscCount = (int) majorSpendingClassificationRepository.count();

        int listSize = msrcList.size();

        int realMonths = listSize / mscCount;

        if (realMonths == 0) return null;

        HashMap<String,Integer> data = new HashMap<>();

        data.put("total",0);

        for (MonthSpendingRecordByClass msrc : msrcList) {
            String name = msrc.getMajorSpendingClass().getName();
            data.put(name,data.getOrDefault(name,0)+msrc.getAmount());
            data.put("total",data.get("total")+msrc.getAmount());
        }

        for (String key : data.keySet()) {
            data.put(key,data.get(key)/realMonths);
        }

        return data;
    }

    public int getThreeMonthIncomeAvg(Member member, LocalDate firstMonth, LocalDate lastMonth) {
        List<MonthIncomeRecord> mirList = monthIncomeRecordRepository.findByMemberAndYmonthBetween(member,firstMonth,lastMonth);
        System.out.println(member.getId() + " " + firstMonth + " " + lastMonth);

        int listSize = mirList.size();

        if (listSize == 0) return -1;

        int total = 0;

        for (MonthIncomeRecord mir : mirList) {
            total += mir.getAmount();
        }

        return total / listSize;
    }

    public List<MSRCDto> thisMonthSpendingByCategory(int year, int month, Member member) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate curMonth = LocalDate.of(localDate.getYear(),localDate.getMonth(),1);

        List<MSRCDto> li = new ArrayList<>();
        List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();

        for (MajorSpendingClassification msc : mscList) {
            MonthSpendingRecordByClass cur = monthSpendingRecordByClassRepository.findByMemberAndYmonthAndMajorSpendingClass(member, curMonth, msc);


            MSRCDto msrcDto = MSRCDto.builder()
                    .amount(cur.getAmount())
                    .category(msc.getName())
                    .build();

            li.add(msrcDto);
        }
        return li;
    }

    public int getMonthIncome(int year, int month, Member member) {
        LocalDate ymonth = LocalDate.of(year,month,1);

        Optional<MonthIncomeRecord> optionalMIR = monthIncomeRecordRepository.findByMemberAndYmonth(member,ymonth);

        if (!optionalMIR.isPresent()) {
            System.out.println("Month Spending Record for member : " + member.getId() + " doesn't exist");
            return -1;
        }

        MonthIncomeRecord mir = optionalMIR.get();

        return mir.getAmount();
    }
}
