package com.example.nthmin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "N-th Minimum Number Finder", description = "API для поиска N-го минимального числа в Excel файле")
public class FileController {
    /*
    по улучшению, добавить базу, кэширование, документирование(javaDoc). Добавил валидацию, чтобы не падало)
     */

    private final FileProcessingService fileProcessingService;

    @GetMapping("/find-nth-min")
    @Operation(summary = "Найти N-е минимальное число",
            description = "Принимает путь к файлу XLSX и число N, возвращает N-е минимальное число")
    public ResponseEntity<?> findNthMinNumber(
            @Parameter(description = "Путь к локальному XLSX файлу") @RequestParam String filePath,
            @Parameter(description = "Порядковый номер минимального числа (N)") @RequestParam int n) {

        try {
            int result = fileProcessingService.findNthMinNumber(filePath, n);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
        //можно добавить GlobalExceptionHandler
    }
}