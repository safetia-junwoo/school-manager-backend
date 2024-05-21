package com.base.boilerplate.file.service;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader {

    public List<List<String>> readExcelFile(MultipartFile file) {
        List<List<String>> dataList = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    // 셀 데이터를 문자열로 변환하여 저장.
                    String cellDate = cellToString(cell);
                    rowData.add(cellDate);
                }
                dataList.add(rowData);
            }
        } catch (Exception e) {
            throw new RuntimeException("excel error");
        }

        return dataList;
    }

    private String cellToString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return cell.getNumericCellValue() + "";
            case BOOLEAN:
                return cell.getBooleanCellValue() + "";
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }
}
