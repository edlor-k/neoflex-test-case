package ru.korablev.spring.neoflextestcase.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private int statusCode;
    private String message;
}
