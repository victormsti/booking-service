package com.bookstar.bookingservice.dto.request;

import lombok.Getter;

import java.time.LocalDate;


@Getter
public class CreateBookingRequest {

    private Long roomId;
    private Integer quantityOfPeople;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
