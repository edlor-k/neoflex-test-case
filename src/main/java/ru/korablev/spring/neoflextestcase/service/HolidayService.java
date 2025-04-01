package ru.korablev.spring.neoflextestcase.service;

import java.time.LocalDate;

public interface HolidayService {
    boolean isHoliday(LocalDate date);
    boolean isWorkingDay(LocalDate date);
    int countWorkingDays(LocalDate startDate, int numberOfDays);
}
