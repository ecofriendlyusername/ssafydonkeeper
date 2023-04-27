package com.ssafy.moneykeeperbackend.budget.repository;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.budget.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByMember(Member member);
}
