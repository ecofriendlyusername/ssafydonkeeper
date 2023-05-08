package com.ssafy.moneykeeperbackend.statistics.fortest;

import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.entity.Asset;
import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
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
import java.util.concurrent.ThreadLocalRandom;

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

    public void generateMockSpendings(Member member) {

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
        for (int i = 1; i <= 5; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(150, 600 + 1);
            LocalDate month = LocalDate.of(2023,i,1);
            MonthIncomeRecord monthIncomeRecord =
                    MonthIncomeRecord.builder()
                            .month(month)
                            .member(member)
                            .amount(randomNum*10000)
                            .build();

            monthIncomeRecordRepository.save(monthIncomeRecord);
        }
    }

    public void buildSpendingGroupForTest() {
        int[] base = {0,300000,400000,500000,1000000,2000000,3000000,4000000,5000000,6000000,7000000};

        int[] below = {300000,400000,500000,1000000,2000000,3000000,4000000,5000000,6000000,7000000,Integer.MAX_VALUE};

        int len = below.length;

        for (int i = 0; i < len; i++) {
            SpendingGroup spendingGroup = SpendingGroup
                    .builder()
                    .below(below[i])
                    .base(base[i])
                    .build();
            spendingGroupRepository.save(spendingGroup);
        }


    }

    public void buildIncomeGroupForTest() {
        int[] base = {0,300000,400000,500000,1000000,2000000,3000000,4000000,5000000,6000000,7000000};

        int[] below = {300000,400000,500000,1000000,2000000,3000000,4000000,5000000,6000000,7000000,Integer.MAX_VALUE};

        int len = below.length;

        for (int i = 0; i < len; i++) {
            IncomeGroup incomeGroup = IncomeGroup
                    .builder()
                    .base(base[i])
                    .below(below[i])
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

        String[] assets = {"하나은행","신한은행","카카오뱅크"};

        for (String asset : assets) {
            buildAsset(asset);
        }
    }

//    private void generateMockMonthSpendingRecords(Member member) {
//        for (int i = 1; i <= 5; i++) {
//            MonthSpendingRecord msr = MonthSpendingRecord.builder()
//                    .groupAvg(-1)
//                    .amount(0)
//                    .member(member)
//                    .ymonth(LocalDate.of(2023,i,1))
//                    .build();
//            monthSpendingRecordRepository.save(msr);
//        }
//    }

    private void generateMockSpendingWith(Member member, int month) {
        String[] months = {"2023-01-","2023-02-","2023-03-","2023-04-","2023-05"};
        String[] days = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};

        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 30; j++) {
                int randomIdx = ThreadLocalRandom.current().nextInt(0, 27 + 1);
                String date = months[i] + days[randomIdx];

                SpendingRequest spendingRequest = SpendingRequest.builder()
                        .amount(ThreadLocalRandom.current().nextInt(8000, 150000 + 1))
                        .detail("some detail")
                        .spendingClassificationId(1L)
                        .assetId(1L)
                        .memo("some memo")
                        .date(date).build();
            }
        }
    }

    public Long generateMockMemberWith(String rs) {
        Member member = Member.builder()
                .email(rs)
                .oauth("oauth")
                .nickname(rs)
                .password(rs)
                .oauthAceessToken("oauthAccessToken")
                .build();

        memberRepository.save(member);
        Member member1 = memberRepository.findByNickname(rs);

        generateMockDataWithMember(member1);

        return member1.getId();
    }

    private void generateMockDataWithMember(Member member) {
        String[] minor = {"식비","술/유흥","카페","생활","온라인/쇼핑","패션/쇼핑",
                "뷰티/미용","교통","자동차","주거/통신","의료/건강","금융","문화/여가","여행/숙박",
                "교육/학습","자녀/육아","반려동물","경조/선물"};

        for (String s : minor) {
            MajorSpendingClassification msc = majorSpendingClassificationRepository.findByName();
        }
        // generateMockMonthSpendingRecords(member1);
        // generateMockMonthSpendingRecordByClasses(member1);
        for (int i = 1; i <= 5; i++) {
            generateMockSpendingWith(member,i);
            generateMockIncomeWith(member,i);
        }
        // generateMockMonthIncomeRecords(member1);

        // updateDataService.determineIncomeGroup(member1,start,end);
        // updateDataService.determineSpendingGroup(member1,start,end);

        // return member.getId();
    }

    private void generateMockIncomeWith(Member member, int i) {
    }


}
