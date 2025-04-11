package ru.korablev.spring.neoflextestcase.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.korablev.spring.neoflextestcase.dto.VacationPayRequest;
import ru.korablev.spring.neoflextestcase.dto.VacationPayResponse;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VacationPayServiceImplTest {

    @Mock
    private HolidayService holidayService;

    @InjectMocks
    private VacationPayServiceImpl vacationPayService;

    @Test
    void calculatesVacationPayForFullWorkingDays() {
        VacationPayRequest request = new VacationPayRequest(50000.0, 10, null);
        VacationPayResponse response = vacationPayService.calculateVacationPay(request);

        assertEquals(17064.8, response.getTotalVacationPay());
    }

    @Test
    void calculatesVacationPayWithStartDateAndHolidays() {
        VacationPayRequest request = new VacationPayRequest(60000.0, 10,
                LocalDate.of(2025, 5, 1));
        when(holidayService.countWorkingDays(LocalDate.of(2025, 5, 1),
                10)).thenReturn(3);

        VacationPayResponse response = vacationPayService.calculateVacationPay(request);

        assertEquals(6143.34, response.getTotalVacationPay());
    }

    @Test
    void calculatesVacationPayWithZeroVacationDays() {
        VacationPayRequest request = new VacationPayRequest(40000.0, 0, null);
        VacationPayResponse response = vacationPayService.calculateVacationPay(request);

        assertEquals(0.0, response.getTotalVacationPay());
    }

    @Test
    void calculatesVacationPayWithNegativeSalary() {
        VacationPayRequest request = new VacationPayRequest(-50000.0, 10, null);
        VacationPayResponse response = vacationPayService.calculateVacationPay(request);

        assertEquals(0, response.getTotalVacationPay());
    }

    @Test
    void calculatesVacationPayWithNullStartDate() {
        VacationPayRequest request = new VacationPayRequest(70000.0, 15, null);
        VacationPayResponse response = vacationPayService.calculateVacationPay(request);

        assertEquals(35836.05, response.getTotalVacationPay());
    }
}