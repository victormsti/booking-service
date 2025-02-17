package com.bookstar.bookingservice.mapper.contract;

import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.model.Booking;

import java.util.List;

public interface BookingMapper {

    BookingResponse toResponse(Booking booking);
    List<BookingResponse> toResponse(List<Booking> bookings);
}
