package ru.korablev.spring.neoflextestcase.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Vacation Pay API",
                description = "API for calculating vacation pay",
                version = "1.0.0",
                contact = @Contact(
                        name = "Vladlen Korablev",
                        email = "korablev.vm@mail.ru"
                )
        )
)
public class OpenApiConfig {
}
