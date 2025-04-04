package ru.korablev.spring.neoflextestcase.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.korablev.spring.neoflextestcase.dto.HolidayResponse;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class HolidayRepositoryImpl implements HolidayRepository {

    private final RestTemplate restTemplate;

    @Override
    public Set<LocalDate> getHolidays(int year) {
        String url = "https://calendar.kuzyak.in/api/calendar/" + year + "/holidays";
        HolidayResponse response = restTemplate.getForObject(url, HolidayResponse.class);
        Set<LocalDate> holidays = new HashSet<>();
        if (response != null && response.getHolidays() != null) {
            response.getHolidays().forEach(holiday -> holidays.add(holiday.getDate().toLocalDate()));
        }
        return holidays;
    }
}