package com.ssafy.moneykeeperbackend.statistics.fortest;

import com.ssafy.moneykeeperbackend.accountbook.entity.*;
import com.ssafy.moneykeeperbackend.accountbook.repository.*;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.*;
import com.ssafy.moneykeeperbackend.statistics.service.ProcessRecordService;
import com.ssafy.moneykeeperbackend.statistics.service.UpdateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
@Service
@RequiredArgsConstructor
public class IndividualTestService {

    private final UpdateDataService updateDataService;

    private final IncomeClassificationRepository incomeClassificationRepository;
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
    public void generateMockSpendingsWith(Member member, boolean isLight) {
//        String[] months = {"2023-01-","2023-02-","2023-03-","2023-04-","2023-05"};
//        String[] days = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};

        int dataCount = isLight ? 5 : 20;

        int minSpending = isLight ? 32000 : 8000;

        int maxSpending = isLight ? 600000 : 150000;

        List<Asset> assets = assetRepository.findByMember(member);

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
                        .asset(assets.get(0))
                        .spendingClassification(scList.get(scIdx))
                        .memo("some memo")
                        .date(today).build();
                spendingRepository.save(spending);

                processSpendingService.processNewSpending(spending,member);
            }
        }
    }

    public void generateMockIncomesWith(Member member) {
        List<IncomeClassification> incomeClassifications = incomeClassificationRepository.findByMember(member);
        List<Asset> assets = assetRepository.findByMember(member);

        for (int i = 2; i <= 5; i++) {
            int day = ThreadLocalRandom.current().nextInt(1, 28 + 1);
            LocalDate today = LocalDate.of(2023,i,day);

            Income income = Income.builder()
                    .amount(ThreadLocalRandom.current().nextInt(1500000, 9500000 + 1))
                    .detail("some detail")
                    .member(member)
                    .asset(assets.get(0))
                    .incomeClassification(incomeClassifications.get(0))
                    .memo("some memo")
                    .date(today).build();
            incomeRepository.save(income);


            processSpendingService.processNewIncome(income,member);
        }
    }
}
