package com.bookstar.bookingservice.dto.request.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class GuestRequest {

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    private Boolean mainGuest;
}
