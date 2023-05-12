package com.ssafy.moneykeeperbackend.statistics.repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.statistics.entity.GroupSpending;
import com.ssafy.moneykeeperbackend.statistics.entity.IncomeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GroupSpendingRepository extends JpaRepository<GroupSpending, Long> {
    List<GroupSpending> findByIncomeGroup(IncomeGroup incomeGroup);

    GroupSpending findByIncomeGroupAndMajorSpendingClass(IncomeGroup incomeGroup, MajorSpendingClassification msc);

    GroupSpending findByIncomeGroupAndMajorSpendingClassAndYmonth(IncomeGroup incomeGroup, MajorSpendingClassification msc, LocalDate thisMonth);

    boolean existsByIncomeGroupAndMajorSpendingClassAndYmonth(IncomeGroup ig, MajorSpendingClassification msc, LocalDate lastMonth);
}
