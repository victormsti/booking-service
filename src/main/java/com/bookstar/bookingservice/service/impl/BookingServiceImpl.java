package com.bookstar.bookingservice.service.impl;

import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.configuration.exception.BadRequestException;
import com.bookstar.bookingservice.configuration.exception.ConflictException;
import com.bookstar.bookingservice.configuration.exception.NotFoundException;
import com.bookstar.bookingservice.dto.request.booking.BookingRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.BookingType;
import com.bookstar.bookingservice.enums.PaymentStatus;
import com.bookstar.bookingservice.mapper.contract.BookingMapper;
import com.bookstar.bookingservice.model.Booking;
import com.bookstar.bookingservice.model.Guest;
import com.bookstar.bookingservice.model.Room;
import com.bookstar.bookingservice.repository.BookingRepository;
import com.bookstar.bookingservice.repository.GuestRepository;
import com.bookstar.bookingservice.repository.RoomRepository;
import com.bookstar.bookingservice.service.contract.BookingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final BookingMapper bookingMapper;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              RoomRepository roomRepository,
                              GuestRepository guestRepository,
                              BookingMapper bookingMapper) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.bookingMapper = bookingMapper;
    }

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        validateBookingDates(request);
        checkRoomAvailabilityForNewBooking(request);
        Room room = getRoom(request.getRoomId());
        validateRoomCapacity(room, request.getQuantityOfPeople());
        BigDecimal finalPrice = calculateFinalPrice(request, room);
        Booking savedBooking = saveBooking(request, room, finalPrice);
        List<Guest> guests = saveGuests(request, savedBooking);
        savedBooking.setGuests(guests);

        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponse updateBooking(Long bookingId, BookingRequest request) {
        validateBookingDates(request);
        checkRoomAvailabilityForUpdateBooking(bookingId, request);
        Room room = getRoom(request.getRoomId());
        validateRoomCapacity(room, request.getQuantityOfPeople());
        BigDecimal finalPrice = calculateFinalPrice(request, room);
        Booking savedBooking = saveBooking(request, room, finalPrice);
        List<Guest> guests = saveGuests(request, savedBooking);
        savedBooking.setGuests(guests);

        return bookingMapper.toResponse(savedBooking);
    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = bookingRepository.findByIdAndUserIdAndStatus(id, UserContext.getInstance().getUser().getId(), BookingStatus.CONFIRMED)
                .orElseThrow(() -> new NotFoundException("No active Booking was found"));

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setPaymentStatus(PaymentStatus.REFUNDED);
        bookingRepository.save(booking);
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findByIdAndUserId(id, UserContext.getInstance().getUser().getId())
                .orElseThrow(() -> new NotFoundException("Booking not found"));

        return bookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAllByUserId(UserContext.getInstance().getUser().getId())
                .orElseGet(ArrayList::new);

        return bookingMapper.toResponse(bookings);
    }

    private void validateBookingDates(BookingRequest request) {
        if (request.getCheckOutDate().isBefore(request.getCheckInDate())) {
            throw new BadRequestException("Check-in date needs to be before check-out date");
        }
    }

    private void checkRoomAvailabilityForNewBooking(BookingRequest request) {
        Optional<List<Booking>> existingBookings = bookingRepository.findActiveBookingsForDates(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate()
        );

        if (existingBookings.isPresent() && !existingBookings.get().isEmpty()) {
            throw new ConflictException("Room is not available for the given dates");
        }
    }

    private void checkRoomAvailabilityForUpdateBooking(Long bookingId, BookingRequest request) {
        Optional<List<Booking>> existingBookings = bookingRepository.findActiveBookingsForDatesExcludingCurrent(
                request.getRoomId(),
                request.getCheckInDate(),
                request.getCheckOutDate(),
                bookingId
        );

        if (existingBookings.isPresent() && !existingBookings.get().isEmpty()) {
            throw new ConflictException("Room is not available for the given dates");
        }
    }

    private Room getRoom(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(
                () -> new NotFoundException("Room was not found")
        );
    }

    private void validateRoomCapacity(Room room, int quantityOfPeople) {
        if (room.getCapacity() < quantityOfPeople) {
            throw new BadRequestException("Room doesn't support the requested amount of people");
        }
    }

    private BigDecimal calculateFinalPrice(BookingRequest request, Room room) {
        long nights = ChronoUnit.DAYS.between(request.getCheckInDate(), request.getCheckOutDate());
        return room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
    }

    private Booking saveBooking(BookingRequest request, Room room, BigDecimal finalPrice) {
        return bookingRepository.save(Booking.builder()
                .type(BookingType.GUEST)
                .user(UserContext.getInstance().getUser())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .room(room)
                .paymentStatus(PaymentStatus.PAID)
                .quantityOfPeople(request.getQuantityOfPeople())
                .finalPrice(finalPrice)
                .status(BookingStatus.CONFIRMED)
                .build());
    }

    private List<Guest> saveGuests(BookingRequest request, Booking savedBooking) {
        List<Guest> guests = request.getGuests().stream()
                .map(guest -> Guest.builder()
                        .firstName(guest.getFirstName())
                        .lastName(guest.getLastName())
                        .birthDate(guest.getBirthDate())
                        .mainGuest(guest.getMainGuest())
                        .booking(savedBooking)
                        .build())
                .toList();

        guestRepository.saveAll(guests);
        return guests;
    }
}
