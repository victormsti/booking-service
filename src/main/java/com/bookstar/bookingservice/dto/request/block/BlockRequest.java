package com.bookstar.bookingservice.dto.request.block;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;


@Getter
public class BlockRequest {

    @NotNull
    private Long roomId;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;
}
