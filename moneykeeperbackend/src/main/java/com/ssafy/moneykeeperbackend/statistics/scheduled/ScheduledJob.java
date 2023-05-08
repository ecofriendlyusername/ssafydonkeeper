package com.ssafy.moneykeeperbackend.statistics.scheduled;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.entity.IncomeGroup;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecord;
import com.ssafy.moneykeeperbackend.statistics.repository.IncomeGroupRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordRepository;
import com.ssafy.moneykeeperbackend.statistics.service.StatService;
import com.ssafy.moneykeeperbackend.statistics.service.UpdateDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ScheduledJob {
    private final MemberRepository memberRepository;
    private final StatService statService;
    private final UpdateDataService updateDataService;
    // @Scheduled(cron = "0 * * 1 * *") // fix later
    public void update() {
        Instant instant = Instant.now();
        System.out.println(instant);

        List<Member> memberList = memberRepository.findAll();

        if (memberList.isEmpty()) {
            System.out.println("updateIncomeGroup.. no members");
            return;
        }

        LocalDate now = LocalDate.now();

        LocalDate lastMonth = now.minusMonths(1);

        LocalDate end = LocalDate.of(lastMonth.getYear(),lastMonth.getMonth(),1);
        LocalDate start = end.minusMonths(3);

        for (Member member : memberList) {
            updateDataService.determineIncomeGroup(member,start,end);
            statService.buildMonthSpendingRecordByClassesForAUser(member,now);
            statService.buildMonthIncomeRecordForAUser(member,now);
            statService.buildMonthSpendingRecordForAUser(member,now);
            updateDataService.determineSpendingGroup(member,start,end);
        }

        updateDataService.updateSpendingCompData();
    }
}
