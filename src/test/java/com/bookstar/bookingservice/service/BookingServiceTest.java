package com.bookstar.bookingservice.service;

import com.bookstar.bookingservice.base.AbstractTest;
import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.mapper.contract.BookingMapper;
import com.bookstar.bookingservice.model.Booking;
import com.bookstar.bookingservice.repository.BookingRepository;
import com.bookstar.bookingservice.repository.RoomRepository;
import com.bookstar.bookingservice.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class BookingServiceTest extends AbstractTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void shouldReturnPagedBookingResponseWhenBookingsExist() {
        UserContext.getInstance().setUser(guestUser);
        Page<Booking> bookingPage = new PageImpl<>(List.of(expectedBooking));

        when(bookingRepository.findBookings(any(), any(), any(), any(), any(), any(), any())).thenReturn(bookingPage);

        Page<BookingResponse> result = bookingService.getAllBookings(null, null, null, null, null, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}
