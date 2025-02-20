package com.bookstar.bookingservice.service;

import com.bookstar.bookingservice.base.AbstractTest;
import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.dto.request.booking.BookingRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.PaymentStatus;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.enums.RoomType;
import com.bookstar.bookingservice.mapper.contract.BookingMapper;
import com.bookstar.bookingservice.model.Booking;
import com.bookstar.bookingservice.repository.BookingRepository;
import com.bookstar.bookingservice.repository.GuestRepository;
import com.bookstar.bookingservice.repository.RoomRepository;
import com.bookstar.bookingservice.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class BookingServiceTest extends AbstractTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void whenCallingMethodCreateBooking_thenItShouldCreateABookingSuccessfully() {
        UserContext.getInstance().setUser(expectedUser);

        when(bookingRepository.findActiveBookingsForDates(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Optional.empty());

        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(expectedRoom));

        when(bookingRepository.save(any(Booking.class))).thenReturn(expectedBooking);

        when(bookingMapper.toResponse(any(Booking.class))).thenReturn(expectedBookingResponse);

        BookingResponse result = bookingService.createBooking(validBookingRequest);

        assertNotNull(result);
        assertEquals(expectedBooking.getId(), result.getId());
    }

    @Test
    void whenCallingMethodUpdateBooking_thenItShouldUpdateABookingSuccessfully() {
        UserContext.getInstance().setUser(expectedUser);

        when(bookingRepository.findActiveBookingsForDatesExcludingCurrent(
                anyLong(), any(LocalDate.class), any(LocalDate.class), any(Long.class)))
                .thenReturn(Optional.empty());

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(expectedBooking));

        when(roomRepository.findById(anyLong())).thenReturn(Optional.of(expectedRoom));

        when(bookingRepository.save(any(Booking.class))).thenReturn(expectedBooking);

        when(bookingMapper.toUpdate(any(Booking.class), any(BookingRequest.class))).thenReturn(expectedBooking);

        when(bookingMapper.toResponse(any(Booking.class))).thenReturn(expectedBookingResponse);

        BookingResponse result = bookingService.updateBooking(expectedBooking.getId(), validBookingRequest);

        assertNotNull(result);
        assertEquals(expectedBooking.getId(), result.getId());
    }

    @Test
    void whenCallingMethodRebookCanceledBooking_thenItShouldRebookBookingSuccessfully() {
        UserContext.getInstance().setUser(expectedUser);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(expectedCanceledBooking));

        when(bookingRepository.findActiveBookingsForDatesExcludingCurrent(
                anyLong(), any(LocalDate.class), any(LocalDate.class), any(Long.class)))
                .thenReturn(Optional.empty());

        when(bookingRepository.save(any(Booking.class))).thenReturn(expectedBooking);

        when(bookingMapper.toResponse(any(Booking.class))).thenReturn(expectedBookingResponse);

        BookingResponse result = bookingService.rebookCanceledBooking(1L);

        assertNotNull(result);
        assertEquals(expectedBooking.getId(), result.getId());
        assertEquals(BookingStatus.CONFIRMED, result.getStatus());
        assertEquals(PaymentStatus.PAID, result.getPaymentStatus());
    }

    @Test
    void whenCallingMethodDeleteBooking_thenItShouldDeleteBookingSuccessfully() {
        UserContext.getInstance().setUser(expectedUser);

        when(bookingRepository.findByIdAndRoomPropertyUserId(anyLong(), anyLong())).thenReturn(Optional.of(expectedCanceledBooking));

        doNothing().when(bookingRepository).delete(any(Booking.class));

        bookingService.deleteBooking(1L);
    }

    @Test
    void whenCallingMethodCancelBooking_thenItShouldCancelBookingSuccessfully() {
        UserContext.getInstance().setUser(expectedUser);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(expectedBooking));

        doNothing().when(bookingRepository).delete(any(Booking.class));

        bookingService.cancelBooking(1L);
    }

    @Test
    void whenCallingMethodGetBookingById_thenItShouldReturnBookingSuccessfully() {
        UserContext.getInstance().setUser(expectedUser);

        when(bookingRepository.findById(anyLong())).thenReturn(Optional.of(expectedBooking));

        when(bookingMapper.toResponse(any(Booking.class))).thenReturn(expectedBookingResponse);

        BookingResponse result = bookingService.getBookingById(1L);

        assertNotNull(result);
        assertEquals(expectedBooking.getId(), result.getId());
    }

    @Test
    void whenCallingMethodGetAllBookings_thenItShouldReturnASuccessfulResponse() {
        UserContext.getInstance().setUser(expectedUser);
        Page<Booking> bookingPage = new PageImpl<>(List.of(expectedBooking));

        when(bookingRepository.findBookings(anyLong(), anyString(), anyString(), any(PropertyType.class), any(RoomType.class),
                any(BookingStatus.class), any(Pageable.class))).thenReturn(bookingPage);

        Page<BookingResponse> result = bookingService.getAllBookings("test", "test",
                PropertyType.HOTEL, RoomType.DOUBLE, BookingStatus.CONFIRMED, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}
