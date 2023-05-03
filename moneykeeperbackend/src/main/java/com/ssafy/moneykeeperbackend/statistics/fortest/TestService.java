package com.ssafy.moneykeeperbackend.statistics.fortest;

import com.ssafy.moneykeeperbackend.accountbook.entity.Asset;
import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.accountbook.repository.AssetRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.entity.*;
import com.ssafy.moneykeeperbackend.statistics.repository.*;
import com.ssafy.moneykeeperbackend.statistics.service.UpdateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TestService {

    private final UpdateDataService updateDataService;

    private final MemberRepository memberRepository;
    private final AssetRepository assetRepository;

    private final MonthSpendingRecordRepository monthSpendingRecordRepository;
    private final SpendingClassificationRepository spendingClassificationRepository;
    // private final IncomeClassificationRepository incomeClassificationRepository;
    private final MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;

    private final MonthIncomeRecordRepository monthIncomeRecordRepository;

    private final SpendingGroupRepository spendingGroupRepository;
    private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;

    private final IncomeGroupRepository incomeGroupRepository;


    public void buildAsset(String asset) {
        Asset asset1 = Asset.builder().name(asset).total_account(234234L).build();
        assetRepository.save(asset1);
    }

//    public void generateMockMonthSpendingRecordByClassesData(Member member) {
////        String[] sclasses = {"외식","배달","영화"};
//        List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();
//        // random.nextInt(max - min + 1) + min
//        Random random = new Random();
//        for (int i = 1; i <= 5; i++) {
//            for (MajorSpendingClassification msc : mscList) {
//                LocalDate month = LocalDate.of(2023,i,1);
//                int amount = (random.nextInt(50)+1)*10000;
//                MonthSpendingRecordByClass msrc = monthSpendingRecordByClassRepository.findByMemberAndYmonthAndMajorSpendingClass(member,month,msc);
//                msrc.
//                monthSpendingRecordByClassRepository.save(monthSpendingRecordByClass);
//
//                addToMonthSpendingRecord(member,month,amount);
//            }
//        }
//    }

    public void generateMockMonthSpendingRecordByClasses(Member member) {
        List<MajorSpendingClassification> mscList = majorSpendingClassificationRepository.findAll();
        // random.nextInt(max - min + 1) + min
        Random random = new Random();
        for (int i = 1; i <= 5; i++) {
            for (MajorSpendingClassification msc : mscList) {
                LocalDate month = LocalDate.of(2023,i,1);
                int amount = (random.nextInt(50)+1)*10000;
                MonthSpendingRecordByClass monthSpendingRecordByClass =
                        MonthSpendingRecordByClass.builder()
                                .majorSpendingClass(msc)
                                .month(month)
                                .member(member)
                                .amount(amount)
                                .build();

                monthSpendingRecordByClassRepository.save(monthSpendingRecordByClass);
//
                addToMonthSpendingRecord(member,month,amount);
            }
        }
    }

    public void addToMonthSpendingRecord(Member member, LocalDate month, int amount) {
        Optional<MonthSpendingRecord> optionalMSR = monthSpendingRecordRepository.findByMemberAndYmonth(member,month);

        if (!optionalMSR.isPresent()) {
            System.out.println("month spending record for member : " + member.getId() + ", with month : " + month + " doesn't exist");
            return;
        }

        MonthSpendingRecord msr = optionalMSR.get();
        int curAmount = msr.getAmount();
        msr.setAmount(curAmount+amount);
    }

    public void generateMockMonthIncomeRecords(Member member) {
        Random random = new Random();
        // System.out.println("generating month income record for : " + member.getId());
        for (int i = 1; i <= 5; i++) {
            LocalDate month = LocalDate.of(2023,i,1);
            MonthIncomeRecord monthIncomeRecord =
                    MonthIncomeRecord.builder()
                            .month(month)
                            .member(member)
                            .amount((random.nextInt(200)+1)*10000)
                            .build();

            monthIncomeRecordRepository.save(monthIncomeRecord);
        }
    }

    public void buildSpendingGroupForTest() {
        int[] spent = {300000,400000,500000,1000000,2000000,3000000,4000000,5000000,6000000,7000000,Integer.MAX_VALUE};

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
        int[] income = {300000,400000,500000,1000000,2000000,3000000,4000000,5000000,6000000,7000000,Integer.MAX_VALUE};

        int len = income.length;

        for (int i = 0; i < len; i++) {
            IncomeGroup incomeGroup = IncomeGroup
                    .builder()
                    .below(income[i])
                    .build();
            incomeGroupRepository.save(incomeGroup);
        }
    }
    public void initCommonForTest() {
        buildSpendingGroupForTest();
        buildIncomeGroupForTest();

        String[] sClassStrs = {"영화","배달","외식","여행","교육"};

        for (String s : sClassStrs) {
            MajorSpendingClassification msc =
                    MajorSpendingClassification.builder()
                            .name(s)
                            .build();
            majorSpendingClassificationRepository.save(msc);
        }

        String[] assets = {"하나은행","신한은행","카카오뱅크"};

        for (String asset : assets) {
            buildAsset(asset);
        }
    }

    private void generateMockMonthSpendingRecords(Member member) {
        for (int i = 1; i <= 5; i++) {
            MonthSpendingRecord msr = MonthSpendingRecord.builder()
                    .groupAvg(-1)
                    .amount(0)
                    .member(member)
                    .ymonth(LocalDate.of(2023,i,1))
                    .build();
            monthSpendingRecordRepository.save(msr);
        }
    }

    public Long generateMockMemberWith(String rs) {
        LocalDate now = LocalDate.now();

        LocalDate lastMonth = now.minusMonths(1);

        LocalDate end = LocalDate.of(lastMonth.getYear(),lastMonth.getMonth(),1);
        LocalDate start = end.minusMonths(3);

        Member member = Member.builder()
                .email(rs)
                .oauth("oauth")
                .nickname(rs)
                .password(rs)
                .oauthAceessToken("oauthAccessToken")
                .build();

        memberRepository.save(member);
        Member member1 = memberRepository.findByNickname(rs);

        generateMockMonthSpendingRecords(member1);
        generateMockMonthSpendingRecordByClasses(member1);
        generateMockMonthIncomeRecords(member1);

        updateDataService.determineIncomeGroup(member1,start,end);
        updateDataService.determineSpendingGroup(member1,start,end);

        return member.getId();
    }
}
