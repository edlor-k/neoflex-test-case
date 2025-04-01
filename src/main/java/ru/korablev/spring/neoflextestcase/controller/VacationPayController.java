package ru.korablev.spring.neoflextestcase.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.korablev.spring.neoflextestcase.dto.VacationPayRequest;
import ru.korablev.spring.neoflextestcase.dto.VacationPayResponse;
import ru.korablev.spring.neoflextestcase.exception.InvalidRequestException;
import ru.korablev.spring.neoflextestcase.service.VacationPayService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VacationPayController {
    private final VacationPayService vacationPayService;

    @GetMapping("/calculate")
    public VacationPayResponse calculateVacationPay(
            @RequestParam double averageSalary,
            @RequestParam int vacationDays,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {
        if (averageSalary <= 0 || vacationDays <= 0) {
            throw new InvalidRequestException("Средняя зарплата и количество отпускных дней должны быть положительными числами.");
        }

        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(averageSalary);
        request.setVacationDays(vacationDays);
        request.setStartDate(startDate);

        return vacationPayService.calculateVacationPay(request);
    }

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleInvalidRequestException(InvalidRequestException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
