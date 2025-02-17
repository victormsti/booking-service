package com.bookstar.bookingservice.dto.response;

import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.BookingType;
import com.bookstar.bookingservice.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookingResponse {

    private Long id;
    private UserResponse user;

    private RoomResponse room;
    private BookingStatus status;
    private BookingType type;
    private Integer quantityOfPeople;
    private LocalDate checkInDate;

    private LocalDate checkOutDate;
    private BigDecimal finalPrice;
    private PaymentStatus paymentStatus;
}
