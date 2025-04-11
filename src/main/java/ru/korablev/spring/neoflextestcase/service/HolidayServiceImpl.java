package ru.korablev.spring.neoflextestcase.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.korablev.spring.neoflextestcase.repository.HolidayRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class HolidayServiceImpl implements HolidayService {
    private final HolidayRepository holidayRepository;
    private final Set<LocalDate> holidays = new HashSet<>();

    @PostConstruct
    public void init() {
        int currentYear = LocalDate.now().getYear();
        int nextYear = currentYear + 1;

        for (int year : new int[]{currentYear, nextYear}) {
            try {
                holidays.addAll(holidayRepository.getHolidays(year));
            } catch (Exception e) {
                log.warn("Failed to fetch holidays for year: {}", year);
            }
        }
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidays.contains(date) || isWeekend(date);
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    @Override
    public boolean isWorkingDay(LocalDate date) {
        return !isHoliday(date);
    }

    @Override
    public int countWorkingDays(LocalDate startDate, int numberOfDays) {
        return startDate.datesUntil(startDate.plusDays(numberOfDays))
                .filter(this::isWorkingDay)
                .mapToInt(date -> 1)
                .sum();
    }
}