package ru.korablev.spring.neoflextestcase.service;

import ru.korablev.spring.neoflextestcase.dto.VacationPayRequest;
import ru.korablev.spring.neoflextestcase.dto.VacationPayResponse;

public interface VacationPayService {
    VacationPayResponse calculateVacationPay(VacationPayRequest vacationPayRequest);
}
