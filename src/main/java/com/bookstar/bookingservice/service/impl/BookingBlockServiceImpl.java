package com.bookstar.bookingservice.service.impl;

import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.configuration.exception.BadRequestException;
import com.bookstar.bookingservice.configuration.exception.ConflictException;
import com.bookstar.bookingservice.configuration.exception.NotFoundException;
import com.bookstar.bookingservice.configuration.exception.UnauthorizedException;
import com.bookstar.bookingservice.dto.request.bookingBlock.BookingBlockRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.BookingType;
import com.bookstar.bookingservice.model.Booking;
import com.bookstar.bookingservice.model.Room;
import com.bookstar.bookingservice.repository.BookingRepository;
import com.bookstar.bookingservice.repository.RoomRepository;
import com.bookstar.bookingservice.service.contract.BookingBlockService;
import com.bookstar.bookingservice.mapper.contract.BookingMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookingBlockServiceImpl implements BookingBlockService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final BookingMapper bookingMapper;

    public BookingBlockServiceImpl(BookingRepository bookingRepository,
                                   RoomRepository roomRepository,
                                   BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    @Transactional
    public BookingResponse createBookingBlock(BookingBlockRequest request) {
        validateBookingDates(request);

        Room room = getRoom(request.getRoomId());
        validateOwnership(room);

        checkRoomAvailabilityForNewBookingBlock(request);

        Booking savedBooking = saveBookingBlock(request, room);
        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponse updateBookingBlock(Long bookingId, BookingBlockRequest request) {
        //TODO update like bookingService
        validateBookingDates(request);

        Room room = getRoom(request.getRoomId());
        validateOwnership(room);

        checkRoomAvailabilityForUpdateBookingBlock(bookingId, request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate());

        Booking updatedBooking = updateBookingBlock(bookingId, request, room);
        return bookingMapper.toResponse(updatedBooking);
    }

    private void validateBookingDates(BookingBlockRequest request) {
        if (request.getCheckOutDate().isBefore(request.getCheckInDate())) {
            throw new BadRequestException("Check-in date must be before check-out date");
        }
    }

    private void checkRoomAvailabilityForNewBookingBlock(BookingBlockRequest request) {
        Optional<List<Booking>> existingBookings = bookingRepository.findActiveBookingsForDates(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        if (existingBookings.isPresent() && !existingBookings.get().isEmpty()) {
            throw new ConflictException("Cannot create a block since the room is busy for the given gates");
        }
    }

    private void checkRoomAvailabilityForUpdateBookingBlock(Long bookingId, Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        Optional<List<Booking>> existingBookings = bookingRepository.findActiveBookingsForDatesExcludingCurrent(
                roomId,
                checkInDate,
                checkOutDate,
                bookingId
        );

        if (existingBookings.isPresent() && !existingBookings.get().isEmpty()) {
            throw new ConflictException("Cannot create a block since the room is busy for the given gates");
        }
    }

    private Room getRoom(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(
                () -> new NotFoundException("Room not found")
        );
    }

    private Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(
                () -> new NotFoundException("Booking not found")
        );
    }

    private Booking saveBookingBlock(BookingBlockRequest request, Room room) {
        Booking booking = Booking.builder()
                .type(BookingType.BLOCK)
                .user(UserContext.getInstance().getUser())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .room(room)
                .status(BookingStatus.BLOCKED)
                .build();

        return bookingRepository.save(booking);
    }

    private Booking updateBookingBlock(Long bookingId, BookingBlockRequest request, Room room) {
        Booking booking = getBooking(bookingId);
        booking = bookingMapper.toUpdate(booking, request);
        booking.setRoom(room);
        return bookingRepository.save(booking);
    }

    private void validateOwnership(Room room) {
        if (!room.getProperty().getUser().getId().equals(UserContext.getInstance().getUser().getId())) {
            throw new UnauthorizedException("You do not have permission to manage booking blocks for this property");
        }
    }
}
