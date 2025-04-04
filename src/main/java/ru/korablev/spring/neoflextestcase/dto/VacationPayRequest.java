package ru.korablev.spring.neoflextestcase.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationPayRequest {
    @Positive(message = "Average salary must be positive")
    private double averageSalary;

    @Positive(message = "Vacation days must be positive")
    private int vacationDays;
    private LocalDate startDate;
}
