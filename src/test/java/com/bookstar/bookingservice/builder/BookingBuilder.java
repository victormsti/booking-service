package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.request.booking.BookingRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.BookingType;
import com.bookstar.bookingservice.enums.PaymentStatus;
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
                .status(BookingStatus.CONFIRMED)
                .paymentStatus(PaymentStatus.PAID)
                .build();
    }

    public static BookingResponse buildCancelledBookingResponse(){
        return BookingResponse.builder()
                .id(1L)
                .type(BookingType.GUEST)
                .checkInDate(LocalDate.of(2025, 4, 18))
                .checkOutDate(LocalDate.of(2025, 4, 22))
                .room(RoomBuilder.buildRoomResponse())
                .user(UserBuilder.buildGuestUserResponse())
                .guests(GuestBuilder.buildGuestResponseList())
                .status(BookingStatus.CANCELLED)
                .paymentStatus(PaymentStatus.REFUNDED)
                .build();
    }

    public static BookingRequest buildBookingRequest(){
        return BookingRequest.builder()
                .roomId(1L)
                .quantityOfPeople(2)
                .checkInDate(LocalDate.of(2025, 4, 18))
                .checkOutDate(LocalDate.of(2025, 4, 22))
                .guests(GuestBuilder.buildGuestRequestList())
                .build();
    }

    public static BookingRequest buildInvalidDatesBookingRequest(){
        return BookingRequest.builder()
                .roomId(1L)
                .quantityOfPeople(2)
                .checkInDate(LocalDate.of(2025, 4, 22))
                .checkOutDate(LocalDate.of(2025, 4, 18))
                .guests(GuestBuilder.buildGuestRequestList())
                .build();
    }

    public static BookingRequest buildInvalidRoomCapacityBookingRequest(){
        return BookingRequest.builder()
                .roomId(1L)
                .quantityOfPeople(30)
                .checkInDate(LocalDate.of(2025, 4, 18))
                .checkOutDate(LocalDate.of(2025, 4, 22))
                .guests(GuestBuilder.buildGuestRequestList())
                .build();
    }

    public static BookingRequest buildUpdateBookingRequest(){
        return BookingRequest.builder()
                .roomId(1L)
                .quantityOfPeople(2)
                .checkInDate(LocalDate.of(2025, 6, 18))
                .checkOutDate(LocalDate.of(2025, 6, 22))
                .guests(GuestBuilder.buildGuestRequestList())
                .build();
    }

    public static BookingRequest buildUpdateBookingBlockingRequest(){
        return BookingRequest.builder()
                .roomId(1L)
                .quantityOfPeople(2)
                .checkInDate(LocalDate.of(2025, 12, 18))
                .checkOutDate(LocalDate.of(2025, 12, 22))
                .guests(GuestBuilder.buildGuestRequestList())
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
                .status(BookingStatus.CONFIRMED)
                .paymentStatus(PaymentStatus.PAID)
                .build();
    }

    public static Booking buildCanceledBooking(){
        return Booking.builder()
                .id(1L)
                .type(BookingType.GUEST)
                .checkInDate(LocalDate.of(2025, 4, 18))
                .checkOutDate(LocalDate.of(2025, 4, 22))
                .guests(GuestBuilder.buildGuest())
                .room(RoomBuilder.buildRoom())
                .user(UserBuilder.buildGuestUser())
                .status(BookingStatus.CANCELLED)
                .paymentStatus(PaymentStatus.REFUNDED)
                .build();
    }
}
