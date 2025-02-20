package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.request.booking.GuestRequest;
import com.bookstar.bookingservice.dto.response.booking.GuestResponse;
import com.bookstar.bookingservice.model.Guest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class GuestBuilder {

    public static List<GuestResponse> buildGuestResponseList(){
        return Arrays.asList(GuestResponse.builder()
                        .id(1L)
                        .mainGuest(Boolean.TRUE)
                        .birthDate(LocalDate.of(1993,3,5))
                        .firstName("John")
                        .lastName("Doe")
                        .build(),
                GuestResponse.builder()
                        .id(2L)
                        .mainGuest(Boolean.FALSE)
                        .birthDate(LocalDate.of(1993,9,23))
                        .firstName("Jane")
                        .lastName("Doe")
                        .build());
    }

    public static List<GuestRequest> buildGuestRequestList(){
        return Arrays.asList(GuestRequest.builder()
                        .mainGuest(Boolean.TRUE)
                        .birthDate(LocalDate.of(1993,3,5))
                        .firstName("John")
                        .lastName("Doe")
                        .build(),
                GuestRequest.builder()
                        .mainGuest(Boolean.FALSE)
                        .birthDate(LocalDate.of(1993,9,23))
                        .firstName("Jane")
                        .lastName("Doe")
                        .build());
    }

    public static List<Guest> buildGuest(){
        return Arrays.asList(Guest.builder()
                        .id(1L)
                        .mainGuest(Boolean.TRUE)
                        .birthDate(LocalDate.of(1993,3,5))
                        .firstName("John")
                        .lastName("Doe")
                        .build(),
                Guest.builder()
                        .id(2L)
                        .mainGuest(Boolean.FALSE)
                        .birthDate(LocalDate.of(1993,9,23))
                        .firstName("Jane")
                        .lastName("Doe")
                        .build());
    }
}
