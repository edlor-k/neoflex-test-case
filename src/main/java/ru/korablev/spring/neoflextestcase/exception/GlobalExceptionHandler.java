package ru.korablev.spring.neoflextestcase.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiError> handleException(InvalidRequestException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        log.error("Validation error: {}", errorMessage, e);
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST.value(), errorMessage),
                HttpStatus.BAD_REQUEST);
    }
}
