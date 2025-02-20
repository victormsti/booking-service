package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.request.bookingBlock.BookingBlockRequest;

import java.time.LocalDate;

public class BookingBlockBuilder {

    public static BookingBlockRequest buildValidBookingBlockRequest(){
        return BookingBlockRequest.builder()
                .roomId(1L)
                .checkInDate(LocalDate.of(2025, 8, 18))
                .checkOutDate(LocalDate.of(2025, 8, 22))
                .build();
    }

    public static BookingBlockRequest buildValidUpdateBookingBlockRequest(){
        return BookingBlockRequest.builder()
                .roomId(1L)
                .checkInDate(LocalDate.of(2025, 10, 18))
                .checkOutDate(LocalDate.of(2025, 10, 22))
                .build();
    }
}
