package com.bookstar.bookingservice.dto.request.booking;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Builder
public class BookingRequest {

    @NotNull
    private Long roomId;

    @NotNull
    private Integer quantityOfPeople;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @NotNull
    private List<GuestRequest> guests;
}
