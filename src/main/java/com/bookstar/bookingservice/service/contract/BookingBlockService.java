package com.bookstar.bookingservice.service.contract;

import com.bookstar.bookingservice.dto.request.bookingBlock.BookingBlockRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;

public interface BookingBlockService {
    BookingResponse createBookingBlock(BookingBlockRequest request);
    BookingResponse updateBookingBlock(Long bookingId, BookingBlockRequest request);
}
