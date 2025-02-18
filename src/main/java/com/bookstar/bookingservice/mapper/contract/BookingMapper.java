package com.bookstar.bookingservice.mapper.contract;

import com.bookstar.bookingservice.dto.request.booking.BookingRequest;
import com.bookstar.bookingservice.dto.request.bookingBlock.BookingBlockRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.model.Booking;

import java.util.List;

public interface BookingMapper {

    BookingResponse toResponse(Booking booking);
    Booking toUpdate(Booking booking, BookingRequest request);
    Booking toUpdate(Booking booking, BookingBlockRequest request);

    List<BookingResponse> toResponse(List<Booking> bookings);
}
