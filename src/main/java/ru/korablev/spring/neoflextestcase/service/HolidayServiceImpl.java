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
    private Set<LocalDate> holidays;

    @PostConstruct
    public void init() {
        holidays = new HashSet<>();
        try {
            holidays.addAll(holidayRepository.getHolidays(LocalDate.now().getYear()));
        } catch (Exception e) {
            log.error("Failed to fetch holidays for the current year from repository", e);
        }
        try {
            holidays.addAll(holidayRepository.getHolidays(LocalDate.now().plusYears(1).getYear()));
        } catch (Exception e) {
            log.error("Failed to fetch holidays for the next year from repository");
        }
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidays.contains(date) ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY ||
                date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

    @Override
    public boolean isWorkingDay(LocalDate date) {
        return !isHoliday(date);
    }

    @Override
    public int countWorkingDays(LocalDate startDate, int numberOfDays) {
        int workingDays = 0;
        LocalDate currentDate = startDate;

        for (int i = 0; i < numberOfDays; i++) {
            if (isWorkingDay(currentDate)) {
                workingDays++;
            }
            currentDate = currentDate.plusDays(1);
        }

        return workingDays;
    }
}