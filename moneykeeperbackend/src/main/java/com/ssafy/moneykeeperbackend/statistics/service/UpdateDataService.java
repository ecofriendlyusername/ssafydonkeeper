package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.entity.IncomeGroup;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthIncomeRecord;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecord;
import com.ssafy.moneykeeperbackend.statistics.entity.SpendingGroup;
import com.ssafy.moneykeeperbackend.statistics.repository.IncomeGroupRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthIncomeRecordRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.SpendingGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateDataService {

    private final MonthIncomeRecordRepository monthIncomeRecordRepository;

    private final MonthSpendingRecordRepository monthSpendingRecordRepository;
    private final MemberRepository memberRepository;

    private final IncomeGroupRepository incomeGroupRepository;

    private final SpendingGroupRepository spendingGroupRepository;

    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;
    public void determineSpendingGroup(Member member, LocalDate start, LocalDate end) { // for test purpose
        List<MonthSpendingRecord> msrList = monthSpendingRecordRepository.findByMemberAndYmonthBetween(member,start,end);

        if (msrList.isEmpty()) {
            System.out.println("No month spending records in the past 3 months to determine spending group");
            return;
        }

        double total = 0;

        for (MonthSpendingRecord msr : msrList) {
            total += msr.getAmount();
        }

        int avg = (int) (total / (double)msrList.size());

        List<SpendingGroup> sgroups = spendingGroupRepository.findAllByOrderByBelowAsc();

        int len = sgroups.size();

        for (int i = 0; i < len-1; i++) {
            SpendingGroup sg = sgroups.get(i);
            if (avg < sg.getBelow()) {
                member.setSpendingGroup(sg);
                memberRepository.save(member);
                return;
            }
        }
        member.setSpendingGroup(sgroups.get(len-1));
        memberRepository.save(member);
    }

    public void updateSpendingCompData() {
        Instant instant = Instant.now();
        System.out.println(instant);

        List<IncomeGroup> allIncomeGroups = incomeGroupRepository.findAll();

        for (IncomeGroup incomeGroup : allIncomeGroups) {
            updateByIncomeGroup(incomeGroup);
        }
    }

    public void updateByIncomeGroup(IncomeGroup incomeGroup) {
        List<Member> membersInIG = memberRepository.findByIncomeGroup(incomeGroup);
        if (membersInIG.size() == 0) return;
        // System.out.println("updateByIncomeGroup.. " + incomeGroup.getBelow());
        LocalDate now = LocalDate.now();
        LocalDate thisMonth = LocalDate.of(now.getYear(),now.getMonth(),1);
        LocalDate lastMonth = thisMonth.minusMonths(1);

        int groupTotal = 0;

        int membersInIGSize = membersInIG.size();

        for (Member member : membersInIG) {
            Optional<MonthSpendingRecord> optionalMSR = monthSpendingRecordRepository.findByMemberAndYmonth(member,lastMonth);
            if (!optionalMSR.isPresent()) {
                System.out.println("Month Spending Record doesn't exist for member : " + member.getId() + " with " + lastMonth);
                membersInIGSize--;
                continue;
            }
            groupTotal += optionalMSR.get().getAmount();
        }

        if (membersInIGSize == 0) {
            System.out.println("group id : " + incomeGroup.getId());
            System.out.println("group below : " + incomeGroup.getBelow());
            System.out.println(membersInIGSize + " " + membersInIG.size());
        }

        int groupAvg = groupTotal / membersInIGSize;

        for (Member member : membersInIG) {
            Optional<MonthSpendingRecord> optionalLastMonth = monthSpendingRecordRepository.findByMemberAndYmonth(member,lastMonth);

            if (!optionalLastMonth.isPresent()) {
                System.out.println("last month record doesn't exist for member " + member.getId());
                continue;
            }

            MonthSpendingRecord msrLastMonth = optionalLastMonth.get();

//            int memberSpending = msrLastMonth.getAmount();
//            int tmp;
//
//            if (memberSpending > groupAvg) {
//                int diff = memberSpending - groupAvg;
//                double p = (double)diff/(double)groupAvg;
//                tmp = (int) (p * 100);
//            } else {
//                int diff = groupAvg - memberSpending;
//                double p = (double)diff/(double)groupAvg;
//                tmp = - (int) (p * 100);
//            }
//            System.out.println("setting month spending record percent for : " + member.getId() + ", with : " + lastMonth + ", percent: " + tmp);
//            if (tmp == 101) {
//                System.out.println("tmp 101, groupAvg : " + groupAvg + ", memberSpending : " + memberSpending);
//            }
            msrLastMonth.setGroupAvg(groupAvg);
            monthSpendingRecordRepository.save(msrLastMonth);
        }
    }

    public void determineIncomeGroup(Member member, LocalDate start, LocalDate end) { // for test purpose
        List<MonthIncomeRecord> monthIncomeRecordList = monthIncomeRecordRepository.findByMemberAndYmonthBetween(member,start,end);

        if (monthIncomeRecordList.size() == 0) {
            System.out.println("no month income record available now for : " + member.getId());
            // ...
            return;
        }

        double totalIncome = 0;

        for (MonthIncomeRecord msr : monthIncomeRecordList) {
            totalIncome += msr.getAmount();
        }

        int avgIncome = (int)(totalIncome / (double)monthIncomeRecordList.size());

        List<IncomeGroup> incomeGroups = incomeGroupRepository.findAllByOrderByBelowAsc();

        if (incomeGroups.size() == 0) {
            System.out.println("no income group for now");
            // ...
            return;
        }

        for (int i = 0; i < incomeGroups.size() - 1; i++) {
            IncomeGroup ig = incomeGroups.get(i);
            if (avgIncome < ig.getBelow()) {
                member.setIncomeGroup(ig);
                memberRepository.save(member);
                return;
            }
        }

        member.setIncomeGroup(incomeGroups.get(incomeGroups.size() - 1));
        memberRepository.save(member);
    }
}
