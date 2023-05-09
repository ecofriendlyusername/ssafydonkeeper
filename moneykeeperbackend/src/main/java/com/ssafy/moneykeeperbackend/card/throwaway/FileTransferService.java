package com.ssafy.moneykeeperbackend.card.throwaway;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@RequiredArgsConstructor
public class FileTransferService {
    @Value("${file.image.path}")
    private String absolutePath;
    public void transferFile() {
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(absolutePath + "/card.txt"), "UTF-8"));

            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(absolutePath + "/card_pure.txt"),"UTF-8"));

            reader.readLine();
            String line = reader.readLine();

            while (line != null) {
                if (oktogo(line)) {
                    writer.write(line);
                    writer.write("\n");
                } else {
                    System.out.println(line);
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean oktogo(String line) {
        if (line.contains("화물복지")) return false;
        if (line.contains("전남")) return false;
        if (line.contains("광주")) return false;
        if (line.contains("부산")) return false;
        if (line.contains("대구")) return false;
        if (line.contains("대전")) return false;
        if (line.contains("비즈니스")) return false;
        if (line.contains("사업자")) return false;
        if (line.contains("전북")) return false;
        if (line.contains("경남")) return false;
        if (line.contains("경북")) return false;
        if (line.contains("연세")) return false;
        if (line.contains("Biz")) return false;
        if (line.contains("Business")) return false;
        String[] s = line.split(",  ");
        if (s.length < 7) return false;
        for (int i = 0; i < 7; i++) {
            if (s[i].length() == 0) return false;
        }
        // 카드 이미지 경로, 카드이름, 카드사, 카테고리, 혜택, 연회비, 전월실적
        return true;
    }
}
