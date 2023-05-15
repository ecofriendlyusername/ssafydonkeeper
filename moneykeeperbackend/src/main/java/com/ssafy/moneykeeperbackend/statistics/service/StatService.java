package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.exception.statistics.FailedToGenerateRecordException;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.dto.*;
import com.ssafy.moneykeeperbackend.statistics.entity.*;
import com.ssafy.moneykeeperbackend.statistics.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatService {
    private final com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;

    private final GenereateRecordService genereateRecordService;
    private final SpendingGroupRepository spendingGroupRepository;

    private final IncomeGroupRepository incomeGroupRepository;

    private final GroupSpendingRepository groupSpendingRepository;

    private final MonthSpendingRecordRepository monthSpendingRecordRepository;
    private final MemberRepository memberRepository;

    private final UpdateDataService updateDataService;

    private final MonthIncomeRecordRepository monthIncomeRecordRepository;

    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;
    public List<CompareWithRecentXDto> compareWithRecentXMonths(int months, Member member) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate curMonth = LocalDate.of(localDate.getYear(),localDate.getMonth(),1);
        LocalDate firstMonth = curMonth.minusMonths(months);
        LocalDate lastMonth = curMonth.minusMonths(1);

        List<CompareWithRecentXDto> li = new ArrayList<>();
        List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();

        for (MajorSpendingClassification msc : mscList) {
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
    }
    public CompareWithUserDto compareWithUsers(int year, int month, Member member) {
        LocalDate ymonth = LocalDate.of(year,month,1);

        Optional<MonthSpendingRecord> optionalMonthSpendingRecord = monthSpendingRecordRepository.findByMemberAndYmonth(member,ymonth);

        if (!optionalMonthSpendingRecord.isPresent()) {
            System.out.println("no spending record for member " + member.getId() + " , with ymonth " + ymonth);
            genereateRecordService.generateRecordForMonth(member, ymonth);
        }

        optionalMonthSpendingRecord = monthSpendingRecordRepository.findByMemberAndYmonth(member,ymonth);

        MonthSpendingRecord msr = optionalMonthSpendingRecord.get();

        IncomeGroup incomeGroup = member.getIncomeGroup();

        if (incomeGroup == null) {
            LocalDate start = ymonth.minusMonths(2);
            List<IncomeGroup> igList = incomeGroupRepository.findAll();
            List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();
            updateDataService.generateGroupSpending(ymonth,mscList,igList);
            updateDataService.determineIncomeGroupAndUpdateGroupSpending(member,start,ymonth,mscList);
        }

        incomeGroup = member.getIncomeGroup();

        List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();

        List<SpendingDataDto> group = new ArrayList<>();
        List<SpendingDataDto> user = new ArrayList<>();

        LocalDate start = ymonth.minusMonths(2);

        int months = 1;

        int groupTotal = 0;

        int userTotal = 0;

        for (MajorSpendingClassification msc : mscList) {
            GroupSpending gs = groupSpendingRepository.findByIncomeGroupAndMajorSpendingClassAndYmonth(incomeGroup,msc,ymonth);

            if (gs == null) {
                throw new NoSuchElementException();
            }
            SpendingDataDto sddGroup = SpendingDataDto.builder()
                    .amount( (int) ((double)gs.getTotal()/(double)gs.getMonths())  )
                    .category(msc.getName())
                    .build();

            group.add(sddGroup);

            List<MonthSpendingRecordByClass> msrcList = monthSpendingRecordByClassRepository.findByMemberAndMajorSpendingClassAndYmonthBetween(member,msc,start,ymonth);

            int total = 0;

            months = gs.getMonths();

            if (months == 0) {
                throw new NoSuchElementException();
            }

            for (MonthSpendingRecordByClass msrc : msrcList) {
                total += msrc.getAmount();
            }
            userTotal += total;

            groupTotal += gs.getTotal();

            SpendingDataDto sddUser = SpendingDataDto.builder()
                    .amount( (int) ((double)total/(double)months)  )
                    .category(msc.getName())
                    .build();

            user.add(sddUser);
        }

        Collections.sort(group, (a,b) -> b.getAmount() - a.getAmount());
        Collections.sort(user, (a,b) -> b.getAmount() - a.getAmount());

        CompareWithUserDto cwud = CompareWithUserDto.builder()
                .base(incomeGroup.getBase())
                .below(incomeGroup.getBelow())
                .user(user)
                .group(group)
                .total((int)((double)userTotal/(double)months))
                .groupAvg((int)((double)groupTotal/(double)months)).build();
        return cwud;
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

        Collections.sort(li, (a,b) -> b.getAmount() - a.getAmount());

        return li;
    }

    public int getMonthIncome(int year, int month, Member member) {
        LocalDate ymonth = LocalDate.of(year,month,1);

        Optional<MonthIncomeRecord> optionalMIR = monthIncomeRecordRepository.findByMemberAndYmonth(member,ymonth);

        if (!optionalMIR.isPresent()) {
            throw new FailedToGenerateRecordException();
        }

        MonthIncomeRecord mir = optionalMIR.get();

        return mir.getAmount();
    }
}
