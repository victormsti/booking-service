package com.bookstar.bookingservice.service.contract;

import com.bookstar.bookingservice.dto.request.booking.CreateBookingRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;

import java.time.LocalDate;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request);
    BookingResponse cancelBooking(Long id);
    BookingResponse getBooking(Long id, LocalDate checkInDate, LocalDate checkOutDate);



}
