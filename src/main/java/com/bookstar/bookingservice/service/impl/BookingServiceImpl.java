package com.bookstar.bookingservice.service.impl;

import com.bookstar.bookingservice.dto.request.CreateBookingRequest;
import com.bookstar.bookingservice.dto.response.BookingResponse;
import com.bookstar.bookingservice.repository.BookingRepository;
import com.bookstar.bookingservice.service.contract.BookingService;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public BookingResponse createBooking(CreateBookingRequest request) {
        return null;
    }
}
