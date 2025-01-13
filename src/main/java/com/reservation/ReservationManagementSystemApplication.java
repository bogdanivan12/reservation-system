package com.reservation;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(title = "Reservation Management System API", version = "1.0",
                description = "API for managing reservations and events.")
)
public class ReservationManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationManagementSystemApplication.class, args);
    }

}
