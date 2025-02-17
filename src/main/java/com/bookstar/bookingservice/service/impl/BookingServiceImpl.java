package com.bookstar.bookingservice.service.impl;

import com.bookstar.bookingservice.dto.request.CreateBookingRequest;
import com.bookstar.bookingservice.dto.response.BookingResponse;
import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.BookingType;
import com.bookstar.bookingservice.enums.PaymentStatus;
import com.bookstar.bookingservice.mapper.contract.BookingMapper;
import com.bookstar.bookingservice.model.Booking;
import com.bookstar.bookingservice.model.Room;
import com.bookstar.bookingservice.repository.BookingRepository;
import com.bookstar.bookingservice.repository.RoomRepository;
import com.bookstar.bookingservice.service.contract.BookingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              RoomRepository roomRepository,
                              BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    public BookingResponse createBooking(CreateBookingRequest request) {
        Optional<List<Booking>> existingBookings = bookingRepository.findActiveBookingsForDates(request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate());

        if(existingBookings.isPresent()){
            throw new RuntimeException("Found bookings for the given dates");
        }

        Room room = roomRepository.findById(request.getRoomId()).orElseThrow(
                () -> new RuntimeException("Room not found by id")
        );

        if(room.getCapacity() < request.getQuantityOfPeople()){
            throw new RuntimeException("Room doesn't support the request amount of people");
        }

        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());

        BigDecimal finalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Booking bookingToSave = Booking.builder()
                .type(BookingType.GUEST)
                .user(null)  //TODO get from userContext
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .room(room)
                .paymentStatus(PaymentStatus.PAID)
                .quantityOfPeople(request.getQuantityOfPeople())
                .finalPrice(finalPrice)
                .status(BookingStatus.CONFIRMED)
                .build();

        return bookingMapper.toResponse(bookingRepository.save(bookingToSave));
    }

    @Override
    public BookingResponse cancelBooking(Long id) {
        return null;
    }

    @Override
    public BookingResponse getBooking(Long id, LocalDate checkInDate, LocalDate checkOutDate) {
        return null;
    }
}
