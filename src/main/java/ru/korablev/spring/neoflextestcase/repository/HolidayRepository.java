package ru.korablev.spring.neoflextestcase.repository;


import java.time.LocalDate;

public interface HolidayRepository {
    boolean isHoliday(LocalDate date);
}