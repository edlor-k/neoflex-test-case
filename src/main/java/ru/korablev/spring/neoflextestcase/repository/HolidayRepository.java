package ru.korablev.spring.neoflextestcase.repository;

import java.time.LocalDate;
import java.util.Set;

public interface HolidayRepository {
    Set<LocalDate> getHolidays(int year);
}