package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.exception.statistics.FailedToGenerateRecordException;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.entity.*;
import com.ssafy.moneykeeperbackend.statistics.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateDataService {

    private final MonthIncomeRecordRepository monthIncomeRecordRepository;

    private final MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;

    private final MonthSpendingRecordRepository monthSpendingRecordRepository;
    private final MemberRepository memberRepository;

    private final GroupSpendingRepository groupSpendingRepository;

    private final IncomeGroupRepository incomeGroupRepository;

    private final SpendingGroupRepository spendingGroupRepository;
    public void determineSpendingGroup(Member member, LocalDate start, LocalDate end) { // for test purpose
        List<MonthSpendingRecord> msrList = monthSpendingRecordRepository.findByMemberAndYmonthBetween(member,start,end);

        if (msrList.isEmpty()) {
            throw new FailedToGenerateRecordException();
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
        List<IncomeGroup> allIncomeGroups = incomeGroupRepository.findAll();

        for (IncomeGroup incomeGroup : allIncomeGroups) {
            updateByIncomeGroup(incomeGroup);
        }
    }

    public void updateByIncomeGroup(IncomeGroup incomeGroup) {
        List<Member> membersInIG = memberRepository.findByIncomeGroup(incomeGroup);
        if (membersInIG.size() == 0) return;
        LocalDate now = LocalDate.now();
        LocalDate thisMonth = LocalDate.of(now.getYear(),now.getMonth(),1);
        LocalDate lastMonth = thisMonth.minusMonths(1);

        int groupTotal = 0;

        int membersInIGSize = membersInIG.size();

        for (Member member : membersInIG) {
            Optional<MonthSpendingRecord> optionalMSR = monthSpendingRecordRepository.findByMemberAndYmonth(member,lastMonth);
            if (!optionalMSR.isPresent()) {
                throw new FailedToGenerateRecordException();
            }
            groupTotal += optionalMSR.get().getAmount();
        }

        int groupAvg = groupTotal / membersInIGSize;

        for (Member member : membersInIG) {
            Optional<MonthSpendingRecord> optionalLastMonth = monthSpendingRecordRepository.findByMemberAndYmonth(member,lastMonth);

            if (!optionalLastMonth.isPresent()) {
                throw new FailedToGenerateRecordException();
            }

            MonthSpendingRecord msrLastMonth = optionalLastMonth.get();

            msrLastMonth.setGroupAvg(groupAvg);
            monthSpendingRecordRepository.save(msrLastMonth);
        }
    }

    public void determineIncomeGroupAndUpdateGroupSpending(Member member, LocalDate start, LocalDate end, List<MajorSpendingClassification> mscList) { // for test purpose
        List<MonthIncomeRecord> monthIncomeRecordList = monthIncomeRecordRepository.findByMemberAndYmonthBetween(member,start,end);

        if (monthIncomeRecordList.size() == 0) {
            throw new FailedToGenerateRecordException();
        }

        double totalIncome = 0;

        for (MonthIncomeRecord msr : monthIncomeRecordList) {
            totalIncome += msr.getAmount();
        }

        int avgIncome = (int)(totalIncome / (double)monthIncomeRecordList.size());

        List<IncomeGroup> incomeGroups = incomeGroupRepository.findAllByOrderByBelowAsc();

        if (incomeGroups.size() == 0) {
            throw new FailedToGenerateRecordException();
        }

        boolean incomeGroupSet = false;

        for (int i = 0; i < incomeGroups.size() - 1; i++) {
            IncomeGroup ig = incomeGroups.get(i);
            if (avgIncome < ig.getBelow()) {
                member.setIncomeGroup(ig);
                memberRepository.save(member);
                incomeGroupSet = true;
                break;
            }
        }

        if (!incomeGroupSet) {
            member.setIncomeGroup(incomeGroups.get(incomeGroups.size() - 1));
            memberRepository.save(member);
        }

        IncomeGroup incomeGroup = member.getIncomeGroup();

        for (MajorSpendingClassification msc : mscList) {
            GroupSpending gs = groupSpendingRepository.findByIncomeGroupAndMajorSpendingClassAndYmonth(incomeGroup,msc,end);

            List<MonthSpendingRecordByClass> msrcList = monthSpendingRecordByClassRepository.findByMemberAndMajorSpendingClassAndYmonthBetween(member,msc,start,end);

            int len = msrcList.size();

            if (gs == null) {
                throw new NoSuchElementException();
            }

            gs.setMonths(len);

            for (MonthSpendingRecordByClass msrc : msrcList) {
                gs.setTotal(msrc.getAmount()+gs.getTotal());
            }

            groupSpendingRepository.save(gs);
        }
    }

    public void cleanGroupSpending() {
        List<GroupSpending> gsList = groupSpendingRepository.findAll();

        for (GroupSpending gs : gsList) {
            gs.setTotal(0);
            gs.setMonths(0);
        }
    }

    public void generateGroupSpending(LocalDate lastMonth, List<MajorSpendingClassification> mscList, List<IncomeGroup> igList) {
        if (groupSpendingRepository.existsByIncomeGroupAndMajorSpendingClassAndYmonth(igList.get(0),mscList.get(0),lastMonth)) return;
        for (MajorSpendingClassification msc : mscList) {
            for (IncomeGroup ig : igList) {
                GroupSpending gs = GroupSpending.builder()
                        .majorSpendingClass(msc)
                        .ymonth(lastMonth)
                        .months(0)
                        .incomeGroup(ig)
                        .total(0)
                        .build();

                groupSpendingRepository.save(gs);
            }
        }
    }
}
