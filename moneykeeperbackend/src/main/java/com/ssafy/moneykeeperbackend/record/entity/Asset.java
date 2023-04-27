package com.ssafy.moneykeeperbackend.record.entity;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asset {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;

    @Builder
    public Asset(String name) {
        this.name = name;
    }
}
