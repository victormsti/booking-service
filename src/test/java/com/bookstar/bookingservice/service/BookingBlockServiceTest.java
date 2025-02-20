package com.bookstar.bookingservice.service;

import com.bookstar.bookingservice.base.AbstractTest;
import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.dto.request.bookingBlock.BookingBlockRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.mapper.contract.BookingMapper;
import com.bookstar.bookingservice.model.Booking;
import com.bookstar.bookingservice.repository.BookingRepository;
import com.bookstar.bookingservice.repository.RoomRepository;
import com.bookstar.bookingservice.service.impl.BookingBlockServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class BookingBlockServiceTest extends AbstractTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingBlockServiceImpl bookingBlockService;

    @Test
    void whenCallingMethodCreateBookingBlock_thenItShouldCreateABookingBlockSuccessfully() {
        UserContext.getInstance().setUser(expectedUser);

        when(bookingRepository.findActiveBookingsForDatesExcludingCurrent(anyLong(), any(LocalDate.class),
                any(LocalDate.class), anyLong())).thenReturn(Optional.empty());

        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(expectedRoom));

        when(bookingRepository.save(any(Booking.class))).thenReturn(expectedBooking);

        when(bookingMapper.toResponse(any(Booking.class))).thenReturn(expectedBookingResponse);

        BookingResponse result = bookingBlockService.createBookingBlock(validBookingBlockRequest);

        assertNotNull(result);
        assertEquals(expectedBooking.getId(), result.getId());
    }

    @Test
    void whenCallingMethodUpdateBookingBlock_thenItShouldUpdateBookingBlockSuccessfully() {
        UserContext.getInstance().setUser(expectedUser);

        when(bookingRepository.findActiveBookingsForDatesExcludingCurrent(
                anyLong(), any(LocalDate.class), any(LocalDate.class), any(Long.class)))
                .thenReturn(Optional.empty());

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(expectedBooking));

        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(expectedRoom));

        when(bookingRepository.save(any(Booking.class))).thenReturn(expectedBooking);

        when(bookingMapper.toUpdate(any(Booking.class), any(BookingBlockRequest.class))).thenReturn(expectedBooking);

        when(bookingMapper.toResponse(any(Booking.class))).thenReturn(expectedBookingResponse);

        BookingResponse result = bookingBlockService.updateBookingBlock(expectedBooking.getId(), validBookingBlockRequest);

        assertNotNull(result);
        assertEquals(expectedBooking.getId(), result.getId());
    }
}
