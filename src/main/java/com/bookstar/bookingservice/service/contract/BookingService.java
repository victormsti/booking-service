package com.bookstar.bookingservice.service.contract;

import com.bookstar.bookingservice.dto.request.CreateBookingRequest;
import com.bookstar.bookingservice.dto.response.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request);
}
