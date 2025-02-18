package com.bookstar.bookingservice.dto.response.booking;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class GuestResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Boolean mainGuest;
}
