package ru.korablev.spring.neoflextestcase.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.korablev.spring.neoflextestcase.dto.VacationPayRequest;
import ru.korablev.spring.neoflextestcase.dto.VacationPayResponse;
import ru.korablev.spring.neoflextestcase.service.VacationPayService;

@RestController
@RequiredArgsConstructor
public class VacationPayController {
    private final VacationPayService vacationPayService;

    @GetMapping("/calculate")
    @ResponseStatus(HttpStatus.OK)
    public VacationPayResponse calculateVacationPay(@Valid VacationPayRequest request) {
        return vacationPayService.calculateVacationPay(request);
    }
}