package ru.korablev.spring.neoflextestcase.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import ru.korablev.spring.neoflextestcase.dto.HolidayResponse;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HolidayRepositoryImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private HolidayRepositoryImpl holidayRepository;

    @Test
    void handlesEmptyHolidayResponse() {
        int year = 2025;
        HolidayResponse mockResponse = new HolidayResponse();
        mockResponse.setHolidays(Collections.emptyList());
        when(restTemplate
                .getForObject("https://calendar.kuzyak.in/api/calendar/2025/holidays", HolidayResponse.class))
                .thenReturn(mockResponse);

        Set<LocalDate> holidays = holidayRepository.getHolidays(year);

        assertTrue(holidays.isEmpty());
    }

    @Test
    void handlesNullHolidayResponse() {
        int year = 2025;
        when(restTemplate
                .getForObject("https://calendar.kuzyak.in/api/calendar/2025/holidays", HolidayResponse.class))
                .thenReturn(null);

        Set<LocalDate> holidays = holidayRepository.getHolidays(year);

        assertTrue(holidays.isEmpty());
    }

    @Test
    void handlesNullHolidayListInResponse() {
        int year = 2025;
        HolidayResponse mockResponse = new HolidayResponse();
        mockResponse.setHolidays(null);
        when(restTemplate
                .getForObject("https://calendar.kuzyak.in/api/calendar/2025/holidays", HolidayResponse.class))
                .thenReturn(mockResponse);

        Set<LocalDate> holidays = holidayRepository.getHolidays(year);

        assertTrue(holidays.isEmpty());
    }

    @Test
    void handlesRestTemplateException() {
        int year = 2025;
        when(restTemplate
                .getForObject("https://calendar.kuzyak.in/api/calendar/2025/holidays", HolidayResponse.class))
                .thenThrow(new RuntimeException("Connection error"));

        Set<LocalDate> holidays = holidayRepository.getHolidays(year);

        assertTrue(holidays.isEmpty(), "Holidays set should be empty when an exception occurs");
    }
}