package ru.korablev.spring.neoflextestcase.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.korablev.spring.neoflextestcase.dto.VacationPayRequest;
import ru.korablev.spring.neoflextestcase.dto.VacationPayResponse;
import ru.korablev.spring.neoflextestcase.service.VacationPayService;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@Tag(name = "Vacation Pay", description = "Main controller for vacation pay calculation")
@RequestMapping("/api/v1")
public class VacationPayController {
    private final VacationPayService vacationPayService;

    @GetMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Calculate vacation pay",
            description = "Calculates the vacation pay based on the provided request data")
    public VacationPayResponse calculateVacationPay(
            @RequestParam @Positive(message = "Average salary must be positive") double averageSalary,
            @RequestParam @Positive(message = "Vacation days must be positive") int vacationDays,
            @RequestParam(required = false) LocalDate startDate) {
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(averageSalary);
        request.setVacationDays(vacationDays);
        request.setStartDate(startDate);
        return vacationPayService.calculateVacationPay(request);
    }
}