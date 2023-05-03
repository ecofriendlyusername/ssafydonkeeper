package com.ssafy.moneykeeperbackend.card.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PutCardService {
    @Value("${file.image.path}")
    private String absolutePath;
    public void saveCards() throws Exception {
        File f = new File(absolutePath);// your folder path

//**Edit** It is array of Strings
        String[] fileList = f.list(); // It gives list of all files in the folder.

        for (String str : fileList) {
            saveCard(absolutePath+ "/" + str);
        }

    }

    public void saveCard(String path) throws Exception {
//        FileReader reader = new FileReader(path);
//        File file = new File(path);
//        char[] chars = new char[file.length()];
//        reader.read(chars);
//        String content = new String(chars);
//        System.out.println(content);
//        reader.close();

//        Scanner sc= new Scanner(file);
//        String input;
//        StringBuffer sb = new StringBuffer();
//        while (sc.hasNextLine()) {
//            input = sc.nextLine();
//            sb.append(input+" ");
//        }
//    }
//        Path filePath = Path.of(path);
//        StringBuilder contentBuilder = new StringBuilder();
//
//        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
//
//            stream.forEach(s -> contentBuilder.append(s).append("\n"));
//        } catch (IOException e) {
//            //handle exception
//        }
//
//        String fileContent = contentBuilder.toString();
        Path fileName
                = Path.of(path);

        // Now calling Files.readString() method to
        // read the file
        String str = Files.readString(fileName);

        System.out.println(str);


    }
}
