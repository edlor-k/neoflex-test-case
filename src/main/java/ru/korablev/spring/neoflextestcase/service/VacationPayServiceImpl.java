package ru.korablev.spring.neoflextestcase.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.korablev.spring.neoflextestcase.dto.VacationPayRequest;
import ru.korablev.spring.neoflextestcase.dto.VacationPayResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class VacationPayServiceImpl implements VacationPayService {

    private final HolidayService holidayService;
    private static final BigDecimal AVERAGE_WORKING_DAYS_IN_MONTH = new BigDecimal("29.3");

    @Override
    public VacationPayResponse calculateVacationPay(VacationPayRequest vacationPayRequest) {
        BigDecimal averageSalary = BigDecimal.valueOf(vacationPayRequest.getAverageSalary());
        if (averageSalary.compareTo(BigDecimal.ZERO) < 0) {
            log.error("Average salary cannot be negative");
            averageSalary = BigDecimal.ZERO;
        }
        int vacationDays = vacationPayRequest.getVacationDays();
        LocalDate startDate = vacationPayRequest.getStartDate();

        BigDecimal averageSalaryPerDay =
                averageSalary.divide(AVERAGE_WORKING_DAYS_IN_MONTH, 2, RoundingMode.FLOOR);

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
