package com.base.boilerplate.file.service;

import com.base.boilerplate.config.GlobalSettings;
import com.base.boilerplate.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class FileStorageService {
    private final GlobalSettings globalSettings;
    private final StringUtils stringUtils;

    public String getFilePath(String filePathCategory) {
        String filePath = globalSettings.getFileUploadPath()+globalSettings.getFileDelimiter()+stringUtils.makeFilePathName(filePathCategory)+globalSettings.getFileDelimiter()+getCurrentDateFormat();

        File file = new File(filePath);
        if(!file.isDirectory()){
            file.mkdirs();
        }

        return filePath;
    }

    public String getCurrentDateFormat(){
        SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
        return date.format(new Date());
    }
}
