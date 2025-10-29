package com.example.nthmin;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class FileProcessingService {

    public int findNthMinNumber(String filePath, int n) throws IOException {
        if (n <= 0) {
            throw new IllegalArgumentException("N должно быть положительным числом");
            //можно сделать кастомное исключение
        }

        Set<Integer> numbers = readNumbersFromExcel(filePath);

        if (numbers.isEmpty()) {
            throw new IllegalArgumentException("Файл не содержит чисел");
        }

        if (n > numbers.size()) {
            throw new IllegalArgumentException("N не может быть больше количества чисел в файле");
        }

        return MinHeapFinder.findNthMin(numbers, n);
    }

    private Set<Integer> readNumbersFromExcel(String filePath) throws IOException {
        Set<Integer> numbers = new HashSet<>();

        try (FileInputStream file = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                    numbers.add((int) cell.getNumericCellValue());
                }
            }
        }
        return numbers;
    }
}