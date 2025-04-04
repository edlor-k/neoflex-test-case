package ru.korablev.spring.neoflextestcase.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.korablev.spring.neoflextestcase.dto.VacationPayRequest;
import ru.korablev.spring.neoflextestcase.dto.VacationPayResponse;
import ru.korablev.spring.neoflextestcase.exception.InvalidRequestException;
import ru.korablev.spring.neoflextestcase.service.VacationPayService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class VacationPayController {
    private final VacationPayService vacationPayService;

    @GetMapping("/calculate")
    public VacationPayResponse calculateVacationPay(@Valid VacationPayRequest request) {
        return vacationPayService.calculateVacationPay(request);
    }
}
