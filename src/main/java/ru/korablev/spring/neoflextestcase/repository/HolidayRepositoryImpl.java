package ru.korablev.spring.neoflextestcase.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import ru.korablev.spring.neoflextestcase.dto.HolidayResponse;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HolidayRepositoryImpl implements HolidayRepository {

    private final RestTemplate restTemplate;

    @Override
    public Set<LocalDate> getHolidays(int year) {
        String url = "https://calendar.kuzyak.in/api/calendar/" + year + "/holidays";
        try {
            HolidayResponse response = restTemplate.getForObject(url, HolidayResponse.class);
            if (response != null && response.getHolidays() != null) {
                return response.getHolidays().stream()
                        .map(holiday -> holiday.getDate().toLocalDate())
                        .collect(Collectors.toSet());
            }
        } catch (RuntimeException e) {
            log.error("Failed to fetch holidays for year {}: {}", year, e.getMessage());
        }
        return new HashSet<>();
    }
}