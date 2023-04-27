package com.ssafy.moneykeeperbackend.record.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncomeClassification {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
}
