package com.bookstar.bookingservice.service;

import com.bookstar.bookingservice.base.AbstractTest;
import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.configuration.exception.BadRequestException;
import com.bookstar.bookingservice.configuration.exception.ConflictException;
import com.bookstar.bookingservice.configuration.exception.NotFoundException;
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
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void whenVerifyingCheckOutAndCheckInDates_thenItShouldThrowBadRequestException() {
        UserContext.getInstance().setUser(expectedUser);

        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            bookingBlockService.createBookingBlock(invalidDatesBookingBlockRequest);
        });

        assertEquals("Check-in date needs to be before check-out date", exception.getMessage());
    }

    @Test
    void whenVerifyingExistingBookingBeforeCreatingBookingBlock_thenItShouldThrowConflictException() {
        UserContext.getInstance().setUser(expectedUser);

        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(expectedRoom));

        when(bookingRepository.findActiveBookingsForDates(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Optional.of(Collections.singletonList(expectedBooking)));

        ConflictException exception = assertThrows(ConflictException.class, () -> {
            bookingBlockService.createBookingBlock(validBookingBlockRequest);
        });

        assertEquals("Cannot create a block since the room is busy for the given gates", exception.getMessage());
    }

    @Test
    void whenFindingRoomById_thenItShouldThrowNotFoundException() {
        UserContext.getInstance().setUser(expectedUser);

        when(bookingRepository.findActiveBookingsForDates(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookingBlockService.createBookingBlock(validBookingBlockRequest);
        });

        assertEquals("Room not found", exception.getMessage());
    }

    @Test
    void whenFindingBookingById_thenItShouldThrowNotFoundException() {
        UserContext.getInstance().setUser(expectedUser);

        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(expectedRoom));

        when(bookingRepository.findActiveBookingsForDatesExcludingCurrent(anyLong(), any(LocalDate.class),
                any(LocalDate.class), anyLong())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            bookingBlockService.updateBookingBlock(99L, validBookingBlockRequest);
        });

        assertEquals("Booking not found", exception.getMessage());
    }

    @Test
    void whenVerifyingExistingBookingBlockBeforeUpdatingBookingBlock_thenItShouldThrowConflictException() {
        UserContext.getInstance().setUser(expectedUser);

        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(expectedRoom));

        when(bookingRepository.findActiveBookingsForDatesExcludingCurrent(anyLong(), any(LocalDate.class),
                any(LocalDate.class), any(Long.class))).thenReturn(Optional.of(Collections.singletonList(expectedBooking)));

        ConflictException exception = assertThrows(ConflictException.class, () -> {
            bookingBlockService.updateBookingBlock(1L, validBookingBlockRequest);
        });

        assertEquals("Cannot create a block since the room is busy for the given gates", exception.getMessage());
    }
}
