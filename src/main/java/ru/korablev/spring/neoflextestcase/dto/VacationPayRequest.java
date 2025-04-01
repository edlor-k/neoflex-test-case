package ru.korablev.spring.neoflextestcase.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationPayRequest {
    private double averageSalary;
    private int vacationDays;
    private LocalDate startDate;
}
