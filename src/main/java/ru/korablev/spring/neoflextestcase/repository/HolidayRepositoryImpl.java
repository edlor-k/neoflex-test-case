package ru.korablev.spring.neoflextestcase.repository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Repository
@RequiredArgsConstructor
@Slf4j
public class HolidayRepositoryImpl implements HolidayRepository {

    @Value("${holidays.file.path}")
    private String holidaysFilePath;

    private final ResourceLoader resourceLoader;
    private final Set<LocalDate> holidays = new HashSet<>();

    @PostConstruct
    public void init() {
        try {
            Resource resource = resourceLoader.getResource(holidaysFilePath);
            if (!resource.exists()) {
                log.error("Файл с выходными не найден: {}", holidaysFilePath);
                return;
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.isBlank()) {
                        try {
                            LocalDate date = LocalDate.parse(line.trim());
                            holidays.add(date);
                        } catch (Exception e) {
                            log.warn("Ошибка парсинга даты: {}", line, e);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("Ошибка при чтении файла", e);
        }
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }
}
