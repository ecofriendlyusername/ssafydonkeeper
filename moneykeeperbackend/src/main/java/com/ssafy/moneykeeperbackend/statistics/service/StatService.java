package com.ssafy.moneykeeperbackend.statistics.service;

import com.ssafy.moneykeeperbackend.record.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.record.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthRecordRepository;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
@RequiredArgsConstructor
public class StatService {

    SpendingClassificationRepository spendingClassificationRepository;

    private final MonthRecordRepository monthRecordRepository;
    public Map<String, int[]> compareWithRecentXMonths(int months) {
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate curMonth = LocalDate.of(localDate.getYear(),localDate.getMonth(),1);
        LocalDate firstMonth = curMonth.minusMonths(months);
        LocalDate lastMonth = curMonth.minusMonths(1);

        Long testId = 1L;

        List<MonthRecord> curMonthRecordList = monthRecordRepository.findByTestIdAndYmonth(testId,curMonth);
 // ---------------------------for test---------------------------
        // List<SpendingClassification> forTest = spendingClassificationRepository.findAll();

 // ---------------------------for test---------------------------
        Map<String,int[]> map = new HashMap<>();

//        for (SpendingClassification spendingClassification : forTest) {
//            map.put(spendingClassification.getName(),new int[3]);
//        }

        int curTotalAmount = 0;

        System.out.println("curMonthRecordList : " + curMonthRecordList.size());

        for (MonthRecord mr : curMonthRecordList) {
            SpendingClassification spendingClassification = mr.getSpendingClassification();
            if (spendingClassification == null) {
                continue;
                // ... ?
            }
            String className = spendingClassification.getName();
            System.out.println("className : " + className);
            if (!map.containsKey(className)) {
                map.put(className,new int[3]);
            }
            map.get(spendingClassification.getName())[0] += mr.getAmount();
        }

        List<MonthRecord> recentMonthsRecordList = monthRecordRepository.findByTestIdAndYmonthBetween(testId,firstMonth,lastMonth);

        int totalSum = 0;

        for (MonthRecord monthRecord : recentMonthsRecordList) {
            SpendingClassification spendingClassification = monthRecord.getSpendingClassification();
            if (spendingClassification == null) {
                continue;
                // ... ?
            }
            String classificationName = spendingClassification.getName();
            if (!map.containsKey(classificationName)) continue;
            int amount = monthRecord.getAmount();
            map.get(classificationName)[1] += amount;
            totalSum += amount;
        }

        int[] data = new int[3];
        data[0] = curTotalAmount;
        data[1] = totalSum / months;

        map.put("total",data);

        for (int[] dt : map.values()) {
            dt[1] = dt[1] / months;
            if (dt[1] < dt[0]) { // 감소
                dt[2] = (int) (100 - ((double)(dt[1]) / (double)(dt[0])) * 100);
                dt[2] = -dt[2];
            } else {
                dt[2] = (int) (((double)(dt[1]) / (double)(dt[0])) * 100 - 100);
            }
        }

        return map;
    }
}
