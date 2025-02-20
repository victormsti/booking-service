package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.request.bookingBlock.BookingBlockRequest;

import java.time.LocalDate;

public class BookingBlockBuilder {

    public static BookingBlockRequest buildBookingBlockRequest(){
        return BookingBlockRequest.builder()
                .roomId(1L)
                .checkInDate(LocalDate.of(2025, 4, 18))
                .checkOutDate(LocalDate.of(2025, 4, 22))
                .build();
    }
}
