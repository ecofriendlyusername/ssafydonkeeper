package com.ssafy.moneykeeperbackend.statistics.fortest;

import com.ssafy.moneykeeperbackend.accountbook.entity.*;
import com.ssafy.moneykeeperbackend.accountbook.repository.*;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.entity.*;
import com.ssafy.moneykeeperbackend.statistics.repository.*;
import com.ssafy.moneykeeperbackend.statistics.service.ProcessRecordService;
import com.ssafy.moneykeeperbackend.statistics.service.UpdateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class TestService { //
    private final UpdateDataService updateDataService;
    private final SpendingRepository spendingRepository;
    private final IncomeRepository incomeRepository;
    private final MemberRepository memberRepository;
    private final AssetRepository assetRepository;
    private final MonthSpendingRecordRepository monthSpendingRecordRepository;
    private final SpendingClassificationRepository spendingClassificationRepository;
    private final MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;

    private final ProcessRecordService processSpendingService;

    private final MonthIncomeRecordRepository monthIncomeRecordRepository;

    private final SpendingGroupRepository spendingGroupRepository;
    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;
    private final IncomeGroupRepository incomeGroupRepository;

    public void buildSpendingGroupForTest() {
        int[] spent = {300000,500000,1000000,3000000,5000000,7000000,Integer.MAX_VALUE};

        int len = spent.length;

        for (int i = 0; i < len; i++) {
            SpendingGroup spendingGroup = SpendingGroup
                    .builder()
                    .below(spent[i])
                    .build();
            spendingGroupRepository.save(spendingGroup);
        }


    }

    public void buildIncomeGroupForTest() {
        int[] below = {300000,500000,1000000,3000000,5000000,7000000,Integer.MAX_VALUE};
        int[] base = {0,300000,500000,1000000,3000000,5000000,7000000};

        int len = below.length;

        for (int i = 0; i < len; i++) {
            IncomeGroup incomeGroup = IncomeGroup
                    .builder()
                    .below(below[i])
                    .base(base[i])
                    .build();
            incomeGroupRepository.save(incomeGroup);
        }
    }
    public void initCommonForTest() {
        buildSpendingGroupForTest();
        buildIncomeGroupForTest();
        String[] major = {"식비","술/유흥","카페","생활","온라인/쇼핑","패션/쇼핑",
                "뷰티/미용","교통","자동차","주거/통신","의료/건강","금융","문화/여가","여행/숙박",
        "교육/학습","자녀/육아","반려동물","경조/선물"};

        for (String s : major) {
            MajorSpendingClassification msc =
                    MajorSpendingClassification.builder()
                            .name(s)
                            .build();
            majorSpendingClassificationRepository.save(msc);
        }
    }

    public void generateMockSpendingsWith(Member member, boolean isLight) {
        int dataCount = isLight ? 5 : 20;

        int minSpending = isLight ? 32000 : 8000;

        int maxSpending = isLight ? 600000 : 150000;

        List<SpendingClassification> scList = spendingClassificationRepository.findByMember(member);
        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j <= dataCount; j++) {
                int day = ThreadLocalRandom.current().nextInt(1, 28 + 1);
                LocalDate today = LocalDate.of(2023,i,day);

                int scIdx = ThreadLocalRandom.current().nextInt(0, 13 + 1);

                Spending spending = Spending.builder()
                        .amount(ThreadLocalRandom.current().nextInt(minSpending, maxSpending + 1))
                        .detail("some detail")
                        .member(member)
                        .spendingClassification(scList.get(scIdx))
                        .memo("some memo")
                        .date(today).build();
                spendingRepository.save(spending);

                processSpendingService.processNewSpending(spending,member);
            }
        }
    }
    public Long generateMockMemberWithString(String rs, List<MajorSpendingClassification> mscList, LocalDate start, LocalDate end, boolean isLight) {
        Member member = Member.builder()
                .email(rs)
                .oauth("oauth")
                .nickname(rs)
                .password(rs)
                .oauthAceessToken("oauthAccessToken")
                .build();

        memberRepository.save(member);
        Member member1 = memberRepository.findByNickname(rs);


        generateMockDataWithMember(member1,mscList,start,end,isLight);

        return member1.getId();
    }

    private void generateMockDataWithMember(Member member, List<MajorSpendingClassification> mscList, LocalDate start, LocalDate end, boolean isLight) {
        for (MajorSpendingClassification msc : mscList) {
            SpendingClassification sc = SpendingClassification.builder()
                    .member(member)
                    .name(msc.getName())
                    .majorSpendingClassification(msc)
                    .build();
            spendingClassificationRepository.save(sc);
        }

        generateMonthSpendingRecord(member,mscList);
        generateMonthIncomeRecord(member);

        generateMockSpendingsWith(member,isLight);
        generateMockIncomesWith(member);

        List<IncomeGroup> igList = incomeGroupRepository.findAll();

        updateDataService.generateGroupSpending(end,mscList,igList);

        updateDataService.determineIncomeGroupAndUpdateGroupSpending(member,start,end,mscList);
    }

    private void generateMonthIncomeRecord(Member member) {
        for (int i = 2; i <= 5; i++) {
            MonthIncomeRecord monthIncomeRecord =
                    MonthIncomeRecord.builder()
                            .month(LocalDate.of(2023,i,1))
                            .member(member)
                            .amount(0)
                            .build();
            monthIncomeRecordRepository.save(monthIncomeRecord);
        }
    }

    private void generateMonthSpendingRecord(Member member, List<MajorSpendingClassification> hm) {
        for (int i = 2; i <= 5; i++) {
            MonthSpendingRecord msr =
                    MonthSpendingRecord.builder()
                            .ymonth(LocalDate.of(2023,i,1))
                            .member(member)
                            .amount(0)
                            .build();
            monthSpendingRecordRepository.save(msr);
        }

        for (MajorSpendingClassification msc : hm) {
            for (int i = 2; i <= 5; i++) {
                LocalDate ymonth = LocalDate.of(2023,i,1);
                MonthSpendingRecordByClass msrc =
                        MonthSpendingRecordByClass.builder()
                                .ymonth(ymonth)
                                .member(member)
                                .majorSpendingClass(msc)
                                .amount(0)
                                .build();
                monthSpendingRecordByClassRepository.save(msrc);
            }
        }
    }

    public void generateMockIncomesWith(Member member) {
        for (int i = 2; i <= 5; i++) {
            int day = ThreadLocalRandom.current().nextInt(1, 28 + 1);
            LocalDate today = LocalDate.of(2023,i,day);

            Income income = Income.builder()
                    .amount(ThreadLocalRandom.current().nextInt(1500000, 9500000 + 1))
                    .detail("some detail")
                    .member(member)
                    .memo("some memo")
                    .date(today).build();
            incomeRepository.save(income);


            processSpendingService.processNewIncome(income,member);
        }
    }
}
