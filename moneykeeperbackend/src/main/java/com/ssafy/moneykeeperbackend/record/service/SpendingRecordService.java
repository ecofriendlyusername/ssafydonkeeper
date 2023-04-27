package com.ssafy.moneykeeperbackend.record.service;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.record.dto.SpendingRequestDto;
import com.ssafy.moneykeeperbackend.record.dto.SpendingResponseDto;
import com.ssafy.moneykeeperbackend.record.entity.Spending;
import com.ssafy.moneykeeperbackend.record.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.record.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.record.repository.SpendingRepository;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthRecord;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpendingRecordService {
    private final MemberRepository memberRepository;

    private final SpendingRepository spendingRepository;
    private final SpendingClassificationRepository spendingClassificationRepository;
    private final MonthRecordRepository monthRecordRepository;
    @Transactional
    public void recordSpending(SpendingRequestDto spendingRequestDto, Long memberId) {
        LocalDate date = LocalDate.of(spendingRequestDto.getYear(),spendingRequestDto.getMonth(),spendingRequestDto.getDay());
        // for test
        SpendingClassification spendingClassification = spendingClassificationRepository.findByName(spendingRequestDto.getClassification());

        if (spendingClassification == null) {
            System.out.println(" spending classification null ");
            // ..
        }

        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if (!optionalMember.isPresent()) {
            System.out.println("member doesn't exist");
            // do something
        }
        Member member = optionalMember.get();
        int amount = spendingRequestDto.getAmount();
        // for test
        Spending spending = Spending.builder()
                .amount(spendingRequestDto.getAmount())
                .date(date)
                .detail(spendingRequestDto.getDetail())
                .memo(spendingRequestDto.getMemo())
                .member(member)
                .spendingClassification(spendingClassification)
                .build();
        LocalDate curMonth = LocalDate.of(spendingRequestDto.getYear(),spendingRequestDto.getMonth(),1);
        MonthRecord monthRecord = monthRecordRepository.findByMemberAndSpendingClassificationAndYmonth(member,spendingClassification,curMonth);
        if (monthRecord == null) {
            // do something

            MonthRecord newMonthRecord = MonthRecord.builder()
                    .month(curMonth)
                    .member(member)
                    .amount(amount)
                    .spendingClassification(spendingClassification)
                    .build();

            monthRecordRepository.save(newMonthRecord);
        } else {
            monthRecord.setAmount(monthRecord.getAmount()+amount);
        }
        spendingRepository.save(spending);
    }

    public void addCatForTest(String cat) {
        SpendingClassification spendingClassification = SpendingClassification.builder().name(cat).build();
        spendingClassificationRepository.save(spendingClassification);
    }

    public List<SpendingResponseDto> viewXMonthSpending(long id, int year, int month) {
        LocalDate start = LocalDate.of(year,month,1);
        LocalDate temp = start.plusMonths(1);
        LocalDate end = temp.minusDays(1);
        Optional<Member> member = memberRepository.findById(id);
        if (!member.isPresent()) {
            System.out.println("no such member");
            // do something
        }
        List<Spending> spendings = spendingRepository.findByMemberAndDateBetween(member.get(),start,end);
        List<SpendingResponseDto> spendingResponseDtos = new ArrayList<>();

        for (Spending spending : spendings) {
            LocalDate localDate = spending.getDate();
            SpendingResponseDto spendingResponseDto = SpendingResponseDto.builder()
                    .day(localDate.getDayOfMonth())
                    .year(year)
                    .month(month)
                    .memberId(spending.getMember().getId())
                    .amount(spending.getAmount())
                    .detail(spending.getDetail())
                    .memo(spending.getMemo())
                    .classification(spending.getSpendingClassification().getName())
                    .build();
            spendingResponseDtos.add(spendingResponseDto);
        }

        return spendingResponseDtos;
    }

//    public SpendingClassification getSpendingClassificationByName(String name) {
//        List<SpendingClassification> spendingClassificationList =
//        for (SpendingClassification spendingClassification : spendingClassificationList) {
//            if (name.equals(spendingClassification.getName())) {
//                return spendingClassification;
//            }
//        }
//        return null;
//    }
}
