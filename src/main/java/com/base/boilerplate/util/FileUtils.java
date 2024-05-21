package com.base.boilerplate.util;

import com.base.boilerplate.auth.exception.CustomFileException;
import com.base.boilerplate.config.GlobalSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final GlobalSettings globalSettings;

    public String getExtension(String originalFileName) throws CustomFileException {
        if(originalFileName!=null) {
            if (originalFileName.indexOf("..") + 1 == originalFileName.lastIndexOf(".")) {
                throw new CustomFileException(HttpStatus.BAD_REQUEST, "파일 이름이 유효하지 얺습니다.");
            }
            return originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
        }
        return "";
    }

    public static String readContent(String path) throws Exception {
        if (!StringUtils.hasText(path)) throw new IllegalArgumentException();

        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            Objects.requireNonNull(FileUtils.class.getResourceAsStream(path)),
                            StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            bufferedReader
                    .lines()
                    .forEach(sb::append);
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("readContent error");
        } finally {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
        }
    }

    public static List<String[]> readCSVContent(String filePath) {
        if (!StringUtils.hasText(filePath)) throw new IllegalArgumentException();

        List<String[]> content = new ArrayList<>();

        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                Files.newInputStream(Paths.get(filePath)),
                "x-windows-949"))) {

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(",");
                content.add(split);
            }
        } catch (IOException e) {
            throw new IllegalStateException("파일을 읽는 중 오류가 발생했습니다.");
        }

        if (!content.isEmpty()) {
            content.remove(0);
        }

        return content;
    }
    public List<MultipartFile> getFileList(MultipartFile[] files) throws CustomFileException {
        List<MultipartFile> multipartFileList = new ArrayList<>();
        if(files!=null){
            if(files.length>5){
                throw new CustomFileException(HttpStatus.BAD_REQUEST, "최대 업로드 파일 갯수를 초과하였습니다.");
            }
            multipartFileList = Arrays.asList(files);
        }
        return multipartFileList;
    }

    public void checkExt(List<MultipartFile> fileList) throws CustomFileException {
        if(!fileList.isEmpty()){
            for(MultipartFile file : fileList){
                String fileName = file.getOriginalFilename();
                assert fileName != null;
                String fileExt = this.getExtension(fileName);
                String[] checkExtList = globalSettings.getFileExtList().split(",");
                if(!Arrays.asList(checkExtList).contains(fileExt.toLowerCase())) {
                    throw new CustomFileException(HttpStatus.BAD_REQUEST, "지원하지 않는 확장자 입니다.");
                }
            }
        }
    }
}
