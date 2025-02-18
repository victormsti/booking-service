package com.bookstar.bookingservice.dto.request.bookingBlock;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class BookingBlockRequest {

    @NotNull
    private Long roomId;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;
}
