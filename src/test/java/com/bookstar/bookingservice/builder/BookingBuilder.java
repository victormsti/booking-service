package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.enums.BookingType;
import com.bookstar.bookingservice.model.Booking;

import java.time.LocalDate;

public class BookingBuilder {
    public static BookingResponse buildBookingResponse(){
        return BookingResponse.builder()
                .id(1L)
                .type(BookingType.GUEST)
                .checkInDate(LocalDate.of(2025, 4, 18))
                .checkOutDate(LocalDate.of(2025, 4, 22))
                .room(RoomBuilder.buildRoomResponse())
                .user(UserBuilder.buildGuestUserResponse())
                .guests(GuestBuilder.buildGuestResponseList())
                .build();
    }

    public static Booking buildBooking(){
        return Booking.builder()
                .id(1L)
                .type(BookingType.GUEST)
                .checkInDate(LocalDate.of(2025, 4, 18))
                .checkOutDate(LocalDate.of(2025, 4, 22))
                .guests(GuestBuilder.buildGuest())
                .room(RoomBuilder.buildRoom())
                .user(UserBuilder.buildGuestUser())
                .build();
    }
}
