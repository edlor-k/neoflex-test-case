package ru.korablev.spring.neoflextestcase.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.korablev.spring.neoflextestcase.repository.HolidayRepository;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HolidayServiceImplTest {

    @Mock
    private HolidayRepository holidayRepository;

    @InjectMocks
    private HolidayServiceImpl holidayService;

    @Test
    void identifiesHolidayFromRepository() {
        LocalDate holiday = LocalDate.of(2025, 4, 11);
        when(holidayRepository.getHolidays(2025)).thenReturn(Set.of(holiday));

        assertTrue(holidayService.isHoliday(holiday));
    }

    @Test
    void identifiesWeekendAsHoliday() {
        holidayService.init();
        LocalDate saturday = LocalDate.of(2025, 4, 12);
        LocalDate sunday = LocalDate.of(2025, 4, 13);

        assertTrue(holidayService.isHoliday(saturday));
        assertTrue(holidayService.isHoliday(sunday));
    }

    @Test
    void identifiesWorkingDay() {
        LocalDate workingDay = LocalDate.of(2023, 10, 9);
        when(holidayRepository.getHolidays(2023)).thenReturn(Set.of());
        holidayService.init();

        assertTrue(holidayService.isWorkingDay(workingDay));
    }

    @Test
    void countsWorkingDaysExcludingHolidaysAndWeekends() {
        LocalDate startDate = LocalDate.of(2025, 4, 11);
        when(holidayRepository.getHolidays(2025))
                .thenReturn(Set.of(LocalDate.of(2025, 4, 14)));
        holidayService.init();

        int workingDays = holidayService.countWorkingDays(startDate, 5);

        assertEquals(2, workingDays);
    }

    @Test
    void handlesEmptyHolidayList() {
        LocalDate date = LocalDate.of(2025, 4, 11);
        when(holidayRepository.getHolidays(2025)).thenReturn(Set.of());
        holidayService.init();

        assertFalse(holidayService.isHoliday(date));
    }

    @Test
    void handlesExceptionDuringHolidayFetch() {
        when(holidayRepository.getHolidays(2025)).thenThrow(new RuntimeException("Error"));
        holidayService.init();

        assertTrue(holidayService.isWorkingDay(LocalDate.of(2025, 4, 11)));
    }
}