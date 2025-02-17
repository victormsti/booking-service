package com.bookstar.bookingservice.dto.request;

import java.time.LocalDate;

public class CreateBookingRequest {

    private Long room_id;
    private Integer quantityOfPeople;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
