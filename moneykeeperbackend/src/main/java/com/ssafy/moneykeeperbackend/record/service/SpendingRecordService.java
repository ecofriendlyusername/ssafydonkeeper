package com.ssafy.moneykeeperbackend.record.service;

import com.ssafy.moneykeeperbackend.record.dto.SpendingRequestDto;
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
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class SpendingRecordService {
    private final SpendingRepository spendingRepository;
    private final SpendingClassificationRepository spendingClassificationRepository;
    private final MonthRecordRepository monthRecordRepository;
    @Transactional
    public void recordSpending(SpendingRequestDto spendingRequestDto) {
        LocalDate date = LocalDate.of(spendingRequestDto.getYear(),spendingRequestDto.getMonth(),spendingRequestDto.getDay());
        // for test
        SpendingClassification spendingClassification = spendingClassificationRepository.findByName(spendingRequestDto.getClassification());

        if (spendingClassification == null) {
            System.out.println(" spending classification null ");
            // do something
        }

        int amount = spendingRequestDto.getAmount();

        Long testId = spendingRequestDto.getTestId();
        // for test
        Spending spending = Spending.builder()
                .amount(spendingRequestDto.getAmount())
                .date(date)
                .detail(spendingRequestDto.getDetail())
                .memo(spendingRequestDto.getMemo())
                .testId(testId)
                .spendingClassification(spendingClassification)
                .build();

        LocalDate curMonth = LocalDate.of(spendingRequestDto.getYear(),spendingRequestDto.getMonth(),1);

        System.out.println(testId + " " + spendingClassification.getName());
        MonthRecord monthRecord = monthRecordRepository.findByTestIdAndSpendingClassificationAndYmonth(testId,spendingClassification,curMonth);
        if (monthRecord == null) {
            // do something

            MonthRecord newMonthRecord = MonthRecord.builder()
                    .month(curMonth)
                    .testId(testId)
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
