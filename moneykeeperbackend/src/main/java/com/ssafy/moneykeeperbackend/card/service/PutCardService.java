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
    }

    public void saveCard(String card) throws Exception {

    }


    private int getMinimumSpending(String substring) throws Exception {
        if (substring.equals("없음")) return 0;
        return Integer.parseInt(substring.split(" ")[0]);
    }
}
