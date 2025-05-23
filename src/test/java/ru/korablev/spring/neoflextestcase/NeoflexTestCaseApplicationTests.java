package ru.korablev.spring.neoflextestcase;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import ru.korablev.spring.neoflextestcase.dto.VacationPayRequest;
import ru.korablev.spring.neoflextestcase.dto.VacationPayResponse;

import java.time.LocalDate;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class NeoflexTestCaseApplicationTests {

    private final TestRestTemplate restTemplate;

    private void assertVacationPay(double salary, int days, double expected) {
        ResponseEntity<VacationPayResponse> response =
                restTemplate
                        .getForEntity("/api/v1/calculate?averageSalary={salary}&vacationDays={days}",
                        VacationPayResponse.class, salary, days);

        assertThat(Objects.requireNonNull(response.getBody()).getTotalVacationPay())
                .isEqualTo(expected);
    }

    @Test
    void testCalculateVacationPayWithoutStartDate() {
        assertVacationPay(29300, 1, 1000.00);
    }

    @Test
    void testCalculateVacationPayWithoutStartDateMultipleDays() {
        assertVacationPay(29300, 5, 5000.00);
    }

    @Test
    void testCalculateVacationPayWithStartDateFourteenDays() {
        assertVacationPay(100000, 14, 47781.44);
    }

    @Test
    void testCalculateVacationPayWithStartDate() {
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(100000);
        request.setVacationDays(1);
        request.setStartDate(LocalDate.of(2025, 5, 5));

        ResponseEntity<VacationPayResponse> response =
                restTemplate
                        .getForEntity("/api/v1/calculate?averageSalary={salary}" +
                                        "&vacationDays={days}&startDate={date}",
                        VacationPayResponse.class, request.getAverageSalary(),
                        request.getVacationDays(), request.getStartDate());
        assertThat(Objects.requireNonNull(response.getBody()).getTotalVacationPay()).isEqualTo(3412.96);
    }

    @Test
    void testCalculateVacationPayWithStartDateMultipleDays() {
        VacationPayRequest request = new VacationPayRequest();
        request.setAverageSalary(29300);
        request.setVacationDays(7);
        request.setStartDate(LocalDate.of(2025, 4, 25));

        ResponseEntity<VacationPayResponse> response =
                restTemplate
                        .getForEntity("/api/v1/calculate?averageSalary={salary}" +
                                        "&vacationDays={days}&startDate={date}",
                        VacationPayResponse.class, request.getAverageSalary(),
                        request.getVacationDays(), request.getStartDate());
        assertThat(Objects.requireNonNull(response.getBody()).getTotalVacationPay()).isEqualTo(4000.00);
    }
}
