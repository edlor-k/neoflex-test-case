package ru.korablev.spring.neoflextestcase.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class HolidayResponse {
    private int year;
    private List<Holiday> holidays;

    @Data
    public static class Holiday {
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        private LocalDateTime date;
        private String name;
    }
}
