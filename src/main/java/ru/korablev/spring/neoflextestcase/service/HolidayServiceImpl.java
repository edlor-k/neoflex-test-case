package ru.korablev.spring.neoflextestcase.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.korablev.spring.neoflextestcase.repository.HolidayRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidayRepository.isHoliday(date) ||
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
