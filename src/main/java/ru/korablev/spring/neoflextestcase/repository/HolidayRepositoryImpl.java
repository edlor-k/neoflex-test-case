package ru.korablev.spring.neoflextestcase.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.korablev.spring.neoflextestcase.dto.HolidayResponse;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HolidayRepositoryImpl implements HolidayRepository {

    private final RestTemplate restTemplate;

    @Override
    public Set<LocalDate> getHolidays(int year) {
        String url = "https://calendar.kuzyak.in/api/calendar/" + year + "/holidays";
        Set<LocalDate> holidays = new HashSet<>();
        try {
            HolidayResponse response = restTemplate.getForObject(url, HolidayResponse.class);
            if (response != null && response.getHolidays() != null) {
                response.getHolidays().forEach(holiday -> holidays.add(holiday.getDate().toLocalDate()));
            }
        } catch (RuntimeException e) {
            log.error("Failed to fetch holidays for year {}: {}", year, e.getMessage());
        }
        return holidays;
    }
}