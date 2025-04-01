package ru.korablev.spring.neoflextestcase.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.korablev.spring.neoflextestcase.dto.VacationPayRequest;
import ru.korablev.spring.neoflextestcase.dto.VacationPayResponse;
import ru.korablev.spring.neoflextestcase.exception.InvalidRequestException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VacationPayServiceImpl implements VacationPayService {

    private final HolidayService holidayService;
    private static final BigDecimal AVERAGE_WORKING_DATES_IN_MONTH = new BigDecimal("20.6");

    @Override
    public VacationPayResponse calculateVacationPay(VacationPayRequest vacationPayRequest) {
        if (vacationPayRequest.getAverageSalary() <= 0 || vacationPayRequest.getVacationDays() <= 0) {
            throw new InvalidRequestException("Средняя зарплата и " +
                    "количество отпускных дней должны быть положительными числами.");
        }

        BigDecimal averageSalary = BigDecimal.valueOf(vacationPayRequest.getAverageSalary());
        int vacationDays = vacationPayRequest.getVacationDays();
        LocalDate startDate = vacationPayRequest.getStartDate();

        BigDecimal averageSalaryPerDay = averageSalary.divide(AVERAGE_WORKING_DATES_IN_MONTH, 2, RoundingMode.FLOOR);

        int workingDays = (startDate != null)
                ? holidayService.countWorkingDays(startDate, vacationDays)
                : vacationDays;

        BigDecimal totalVacationPay = averageSalaryPerDay
                .multiply(BigDecimal.valueOf(workingDays))
                .setScale(2, RoundingMode.HALF_UP);

        VacationPayResponse vacationPayResponse = new VacationPayResponse();
        vacationPayResponse.setTotalVacationPay(totalVacationPay.doubleValue());

        return vacationPayResponse;
    }
}
