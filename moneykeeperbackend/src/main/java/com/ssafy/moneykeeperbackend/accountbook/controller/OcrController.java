package com.ssafy.moneykeeperbackend.accountbook.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account-book/ocr")
public class OcrController {

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String performOCR(MultipartFile image) throws IOException {
		if (image == null || image.isEmpty()) {
			return "이미지 데이터가 없습니다.";
		}

		File imageFile = convertMultipartFileToFile(image);

		// Tesseract OCR 인스턴스 생성
		Tesseract tesseract = new Tesseract();

		// Tesseract OCR 데이터 경로 설정 (tessdata 폴더에 해당 언어 데이터가 있어야 함)
		String projectPath = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리 경로
		String tessDataPath = Paths.get(projectPath, "src", "main", "resources", "tessdata").toString();
		tesseract.setDatapath(tessDataPath);

		System.out.println("tessDataPath");
		System.out.println(tessDataPath);

		tesseract.setLanguage("kor"); // 영어, 한국어, OSD 언어 설정
		tesseract.setTessVariable("user_defined_dpi", "300"); // 해상도 값을 설정합니다.

		try {
			// 이미지 파일이 유효한지 검사
			if (!imageFile.isFile() || !imageFile.exists()) {
				return "유효한 이미지 파일이 아닙니다.";
			}

			String result = tesseract.doOCR(imageFile);
			System.out.println("result");
			System.out.println(result);
			return result;
		} catch (TesseractException e) {
			e.printStackTrace();
			return "OCR 처리 중 오류가 발생했습니다.";
		} finally {
			if (imageFile != null) {
				imageFile.delete();
			}
		}
	}

	private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
		String fileName = "ocr_" + System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();

		String projectPath = System.getProperty("user.dir"); // 현재 프로젝트 디렉토리 경로
		String TEMP_FOLDER = Paths.get(projectPath, "src", "main", "resources", "img").toString();
		File file = new File(TEMP_FOLDER, fileName);

		try (FileOutputStream fos = new FileOutputStream(file)) {
			FileCopyUtils.copy(multipartFile.getInputStream(), fos);
		}

		return file;
	}

}
