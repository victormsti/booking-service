package com.bookstar.bookingservice.service.contract;

import com.bookstar.bookingservice.dto.request.booking.BookingRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.enums.RoomType;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);
    BookingResponse updateBooking(Long bookingId, BookingRequest request);
    BookingResponse rebookCanceledBooking(Long id);
    void deleteBooking(Long id);
    void cancelBooking(Long id);
    BookingResponse getBookingById(Long id);
    Page<BookingResponse> getAllBookings(
            String propertyName, String roomName, PropertyType propertyType,
            RoomType roomType, BookingStatus availability, int page, int size);

}
