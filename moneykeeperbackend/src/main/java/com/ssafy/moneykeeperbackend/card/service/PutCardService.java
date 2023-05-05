package com.ssafy.moneykeeperbackend.card.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PutCardService {
    HashSet<String> companies = new HashSet<>();
    HashSet<String> keywords = new HashSet<>();

    HashSet<Character> everyKoreanChars = new HashSet<>();

    boolean[] skip = new boolean[60000];
    @Value("${file.image.path}")
    private String absolutePath;
    public void saveCards() throws Exception {
        for (int i = 0; i <= 127; i++) skip[i] = true;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(absolutePath + "/cards.txt"), "UTF-8"));

            reader.readLine();
            String line = reader.readLine();

            while (line != null) {
                saveCard(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        for (String company : companies) {
//            System.out.print(company + " ");
//        }
        for (String keyword : keywords) System.out.print(keyword + " ");

        System.out.println();
    }

    public void saveCard(String card) throws Exception {
        int i = 0;
        int len = card.length();
        int j = i;
        while (card.charAt(j) != ',') j++;
        String name = card.substring(i,j);
        if (name.length() == 0 || card.charAt(j+1) == ',') return;
        j += 2;
        i = j;
        while (card.charAt(j) != ',') j++;
        String company = card.substring(i,j);

        int bsi = i + 1; // benefit start index

        i = len-1;

        while (card.charAt(i) != ',') i--;


        int minimunSpending = -1;

        try {
            getMinimumSpending(card.substring(i+2,len));
        } catch (Exception e) {
            return;
        }

        int annualFee;

        int ani = card.indexOf("국내전용");
        if (ani == -1) ani = card.indexOf("해외겸용");

        int bei = ani-1;
        if (ani == -1) return;
        StringBuilder sb = new StringBuilder();
        i = ani + 4;
        annualFee = card.charAt(i) != ' ' ? Integer.parseInt(findFee(i,card)) : Integer.parseInt(findFee(i+1,card));

        getKeywords(bsi,bei,card);
        System.out.println("카드명: " + name);
        System.out.println("회사: " + company);
        System.out.println("연회비: " + annualFee);
        System.out.println();
        companies.add(company);
    }

    private void getKeywords(int bsi, int bei, String card) {
        int i = bsi;
        StringBuilder keyword = new StringBuilder();
        while (i <= bei) {
            if (skip[card.charAt(i)]) {
                if (keyword.length() != 0) {
                    keywords.add(keyword.toString());
                }
                keyword = new StringBuilder();
            } else {
               keyword.append(card.charAt(i));
            }
            i++;
        }
    }

    private String findFee(int i, String card) {
        if (card.charAt(i) == '없') return "0";
        StringBuilder sb = new StringBuilder();
        while (card.charAt(i) != '원') {
            if (card.charAt(i) == ',') {
                i++;
            } else {
                sb.append(card.charAt(i++));
            }
        }
        return sb.toString().trim();
    }

    private int getMinimumSpending(String substring) throws Exception {
        if (substring.equals("없음")) return 0;
        return Integer.parseInt(substring.split(" ")[0]);
    }

//    public void testenum() {
//        String
//    }
}
