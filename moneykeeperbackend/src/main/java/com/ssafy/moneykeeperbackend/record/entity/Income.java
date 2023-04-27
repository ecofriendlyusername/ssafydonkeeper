package com.ssafy.moneykeeperbackend.record.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Income {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @NonNull
    private IncomeClassification incomeClassification;
}
