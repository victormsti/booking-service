package com.bookstar.bookingservice.service.contract;

import com.bookstar.bookingservice.dto.request.booking.CreateBookingRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request);
    void cancelBooking(Long id);
    BookingResponse getBookingById(Long id);

    // TODO change to pageable
    List<BookingResponse> getAllBookings();
}
