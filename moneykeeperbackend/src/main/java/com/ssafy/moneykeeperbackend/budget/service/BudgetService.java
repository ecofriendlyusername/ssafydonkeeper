package com.ssafy.moneykeeperbackend.budget.service;

import com.ssafy.moneykeeperbackend.budget.dto.BudgetResponseDto;
import com.ssafy.moneykeeperbackend.budget.entity.Account;
import com.ssafy.moneykeeperbackend.budget.entity.Budget;
import com.ssafy.moneykeeperbackend.budget.repository.AccountRepository;
import com.ssafy.moneykeeperbackend.budget.repository.BudgetRepository;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final MemberRepository memberRepository;

    private final AccountRepository accountRepository;
    public BudgetResponseDto getBudget(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        if (!optionalMember.isPresent()) {
            // do something
        }
        Budget budget = budgetRepository.findByMember(optionalMember.get());
        LocalDate date = budget.getDate();
        int year = date.getYear();
        int month = date.getMonthValue();
        BudgetResponseDto budgetResponseDto = BudgetResponseDto.builder()
                .year(year)
                .month(month)
                .budget(budget.getBudget())
                .spending(budget.getSpending())
                .build();

        return budgetResponseDto;
    }

    public void setBudget(Long id, int budgetAmount) {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(now.getYear(),now.getMonth(),1);
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (!optionalMember.isPresent()) {
            // do something
            System.out.println("member with member id " + id + " doesn't exist");
        }
        Member member = optionalMember.get();
        Budget budget = budgetRepository.findByMemberAndDate(member,date);

        if (budget == null) {
            Budget newBudget = Budget.builder()
                    .member(member)
                    .spending(0)
                    .budget(budgetAmount)
                    .date(date)
                    .build();

            budgetRepository.save(newBudget);
        } else {
            budget.setBudget(budgetAmount);
        }
    }

    public void setAccount(Long id, int initialAmount) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (!optionalMember.isPresent()) {
            // do something
            System.out.println("member with member id " + id + " doesn't exist");
        }
        Member member = optionalMember.get();
        Account account = accountRepository.findByMember(member);

        if (account != null) return;

            Account newAccount = Account.builder()
                    .member(member)
                    .balance(initialAmount)
                    .build();
            accountRepository.save(newAccount);
    }
    @Transactional
    public void updateBudgetAndAccountAfterSpending(Member member, LocalDate curMonth, int amount) {
        System.out.println("asdf");
        Budget budget = budgetRepository.findByMemberAndDate(member,curMonth);
        if (budget == null) {
            // throw an exception
            System.out.println("budget doesn't exist");
            System.out.println("memberId : " + member.getId());
            System.out.println("curMonth : " + curMonth);
            return;
            // for test
        }
        System.out.println("asdf2");
        Account account = accountRepository.findByMember(member);
        if (account == null) {
            // throw an exception
            System.out.println("account doesn't exist");
            System.out.println("memberId : " + member.getId());
            return;
            // for test
        }
        System.out.println("asdf3");
        if (budget == null) {
            // throw an exception
            System.out.println("budget doesn't exist");
            System.out.println("memberId : " + member.getId());
            System.out.println("curMonth : " + curMonth);
            return;
            // for test
        }
        System.out.println("asdf4");
        budget.setSpending(budget.getSpending()+amount);
        account.setBalance(account.getBalance()-amount);
        System.out.println("asdf5");
    }
}
