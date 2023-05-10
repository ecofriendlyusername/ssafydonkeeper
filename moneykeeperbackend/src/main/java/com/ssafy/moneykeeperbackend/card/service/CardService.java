package com.ssafy.moneykeeperbackend.card.service;

import com.ssafy.moneykeeperbackend.card.dto.CardDto;
import com.ssafy.moneykeeperbackend.card.entity.Card;
import com.ssafy.moneykeeperbackend.card.repository.CardRepository;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.statistics.repository.MonthSpendingRecordByClassRepository;
import com.ssafy.moneykeeperbackend.statistics.service.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    private final MonthSpendingRecordByClassRepository monthSpendingRecordByClassRepository;

    private final StatService statService;

    private final MemberRepository memberRepository;

    public List<CardDto> getCards(Member member) {
        LocalDate now = LocalDate.now();

        LocalDate lastMonthSameDay = now.minusMonths(1);

        LocalDate lastMonth = LocalDate.of(lastMonthSameDay.getYear(),lastMonthSameDay.getMonth(),1);
        LocalDate firstMonth = lastMonth.minusMonths(2);


        HashMap<String,Integer> spendingAvgsByClasses = statService.getThreeMonthSpendingAvgByClass(member,firstMonth,lastMonth);


        if (spendingAvgsByClasses == null) {
            return null;
        }

        int incomeAvg = statService.getThreeMonthIncomeAvg(member,firstMonth,lastMonth);

        int spendingAvgTotal = spendingAvgsByClasses.get("total");

        int isCredit = spendingAvgTotal >= incomeAvg ? 0 : 1;
        
        int annualFee = (int) (((double) incomeAvg) * 0.03);

        List<Card> cardList = cardRepository.findByIsCreditAndAnnualFeeLessThanAndMinimumSpendingLessThan(isCredit,annualFee,spendingAvgTotal);

        if (cardList.size() == 0) {
            return getDisplayCard();
        }

        for (Card card : cardList) {
            System.out.println(card.getName() + " " + card.getBenefitDetail() + " " + card.getBenefitImportant());
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)-> (a[0]==b[0] ? (a[1]==b[1] ? b[2] - a[2] : b[1] - a[1]) : b[0] - a[0]));

        System.out.println("spendingAvgTotal : " + spendingAvgTotal);
        System.out.println("incomeAvg : " + incomeAvg);
        System.out.println("annualFee : " + annualFee);
        System.out.println("isCredit : " + isCredit);
        System.out.println("size : " + cardList.size());

        for (int i = 0; i < cardList.size(); i++) {
            Card card = cardList.get(i);
            int[] a = new int[4];
            a[0] = similarity(card,spendingAvgsByClasses);
            a[1] = card.getMinimumSpending();
            a[2] = card.getAnnualFee();
            a[3] = i;
            pq.offer(a);
        }

        List<CardDto> cardDtoList = new ArrayList<>(10);

        int i = 0;
        while (i < 10 && !pq.isEmpty()) {
            int cardNum = pq.poll()[3];
            Card card = cardList.get(cardNum);

            CardDto cardDto = CardDto.builder()
                    .id(card.getId())
                    .company(card.getCompany())
                    .name(card.getName())
                    .benefits(card.getBenefitDetail())
                    .minimumSpending(card.getMinimumSpending())
                    .annualFee(card.getAnnualFee())
                    .imgPath(card.getImgPath())
                    .build();

            cardDtoList.add(cardDto);

            i++;
        }

        return cardDtoList;
    }

    private List<CardDto> getDisplayCard() {
        String[] displayCards = {"삼성카드 taptap O","청춘대로 싱글 체크카드","MULTI Young(멀티 영) 카드",
                "IBK 무직타이거 카드(신용)","I’m YOLO 플래티넘","삼성플래티늄체크카드"};

        List<CardDto> cardDtoList = new ArrayList<>();

        for (String displayCard : displayCards) {
            Card card = cardRepository.findByName(displayCard);

            CardDto cardDto = CardDto.builder()
                    .id(card.getId())
                    .company(card.getCompany())
                    .name(card.getName())
                    .benefits(card.getBenefitDetail())
                    .minimumSpending(card.getMinimumSpending())
                    .annualFee(card.getAnnualFee())
                    .imgPath(card.getImgPath())
                    .build();
            cardDtoList.add(cardDto);
        }

        return cardDtoList;
    }

    private int similarity(Card card, HashMap<String,Integer> spendingAvgByClasses) {
        int dotProduct = 0;

        String[] benefitStr = card.getBenefitImportant().split(" ");

        for (String benefit : benefitStr) {
            String[] parsedBenefit = benefit.split(":");
            int a = spendingAvgByClasses.get(parsedBenefit[0]);
            int b = Integer.parseInt(parsedBenefit[1]);
            dotProduct += a * b;
        }

        return dotProduct;
    }

    public CardDto getCard(Long id) {
        Optional<Card> cardOptional = cardRepository.findById(id);

        if (cardOptional.isEmpty()) return null;

        Card card = cardOptional.get();

        CardDto cardDto = CardDto.builder()
                .company(card.getCompany())
                .name(card.getName())
                .benefits(card.getBenefitDetail())
                .minimumSpending(card.getMinimumSpending())
                .annualFee(card.getAnnualFee())
                .imgPath(card.getImgPath())
                .build();

        return cardDto;
    }
}
