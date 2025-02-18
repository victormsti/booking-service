package com.bookstar.bookingservice.service.contract;

import com.bookstar.bookingservice.dto.request.block.BlockRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;

public interface BookingBlockService {
    BookingResponse createBookingBlock(BlockRequest request);
    BookingResponse updateBookingBlock(Long bookingId, BlockRequest request);
    void deleteBookingBlock(Long id);
}
