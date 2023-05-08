package com.ssafy.moneykeeperbackend.card.service;

import com.ssafy.moneykeeperbackend.card.entity.Card;
import com.ssafy.moneykeeperbackend.card.enums.Benefit;
import com.ssafy.moneykeeperbackend.card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PutCardService {
    HashSet<String> bfs = new HashSet<>();
    private final CardRepository cardRepository;

    boolean[] skip = new boolean[60000];
    @Value("${file.image.path}")
    private String absolutePath;
    public void saveCards() throws Exception {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(absolutePath + "/cards.txt"), "UTF-8"));

            reader.readLine();
            String line = reader.readLine();

            while (line != null) {
                try {saveCard(line);}
                catch (Exception e) {}
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String keyword : bfs) System.out.print(keyword + " ");

        System.out.println("done");
    }

    public void saveCard(String card) throws Exception {
        String[] temp = card.split(",  ");

        String imgPath = temp[0];
        String name = temp[1];
        int isCredit = name.contains("check") || name.contains("체크") ? 0 : 1;
        String company = temp[2];

        String[] benefits = temp[3].split("\\?");

        int fee;

        if (temp[5].charAt(0) == '없') fee = 0;
        else {
            int i = temp[5].charAt(4) == ' ' ? 5 : 4;
            if (temp[5].charAt(i) == '없') fee = 0;
            else {
                try {StringBuilder sb = new StringBuilder();
                while (i < temp[5].length()) {
                    char c = temp[5].charAt(i);
                    if (c == '원' || c == ' ') break;
                    if (c == ',') {
                        i++;
                        continue;
                    }
                    sb.append(c);
                    i++;
                }
                fee = Integer.parseInt(sb.toString());}
                catch (Exception e) {
                    return;
                }
            }
        }

        String[] str = temp[6].split(" ");

        int minimum = str.length == 1 ? 0 : Integer.parseInt(str[0]) * 10000;

        String[] benefitDetails = temp[4].split("\\?");

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < benefitDetails.length-1; i++) {
            sb.append(benefitDetails[i] + "\n");
        }
        sb.append(benefitDetails[benefitDetails.length-1]);

        HashMap<String,Integer> hm = new HashMap<>();

        if (benefits.length >= 1) {
            String benefitUp = benefits[0].toUpperCase();
            String benefit = Benefit.valueOf(benefitUp).getLabel();
            hm.put(benefit,hm.getOrDefault(benefit,0)+1);
        }
        if (benefits.length >= 2) {
            String benefitUp = benefits[1].toUpperCase();
            String benefit = Benefit.valueOf(benefitUp).getLabel();
            hm.put(benefit,hm.getOrDefault(benefit,0)+1);
        }
        if (benefits.length >= 3) {
            String benefitUp = benefits[2].substring(0, benefits[2].length()).toUpperCase();
            String benefit = Benefit.valueOf(benefitUp).getLabel();
            hm.put(benefit,hm.getOrDefault(benefit,0)+1);
        }

        StringBuilder sb2 = new StringBuilder();

        for (String key : hm.keySet()) {
            sb2.append(key + ":" + hm.get(key)+ " ");
        }

        Card cardObject = Card.builder()
                .name(name)
                .company(company)
                .annualFee(fee)
                .minimumSpending(minimum)
                .benefitDetail(sb.toString())
                .isCredit(isCredit)
                .imgPath(imgPath)
                .benefitImportant(sb2.toString())
                .build();

        cardRepository.save(cardObject);

        System.out.println();
    }
}
