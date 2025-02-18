package com.bookstar.bookingservice.service.contract;

import com.bookstar.bookingservice.dto.request.booking.BookingRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);
    BookingResponse updateBooking(Long bookingId, BookingRequest request);
    void cancelBooking(Long id);
    BookingResponse getBookingById(Long id);

    // TODO change to pageable
    List<BookingResponse> getAllBookings();

}
