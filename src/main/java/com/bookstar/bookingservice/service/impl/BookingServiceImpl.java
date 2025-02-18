package com.bookstar.bookingservice.service.impl;

import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.configuration.exception.BadRequestException;
import com.bookstar.bookingservice.configuration.exception.ConflictException;
import com.bookstar.bookingservice.configuration.exception.NotFoundException;
import com.bookstar.bookingservice.configuration.exception.UnauthorizedException;
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
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        checkRoomAvailabilityForUpdateBooking(bookingId, request.getRoomId(), request.getCheckInDate(), request.getCheckOutDate());
        Room room = getRoom(request.getRoomId());
        validateRoomCapacity(room, request.getQuantityOfPeople());
        BigDecimal finalPrice = calculateFinalPrice(request, room);
        Booking updatedBooking = updateBooking(getBooking(bookingId), request, room, finalPrice);
        List<Guest> guests = saveGuests(request, updatedBooking);
        updatedBooking.getGuests().clear();
        updatedBooking.getGuests().addAll(guests);
        return bookingMapper.toResponse(bookingRepository.save(updatedBooking));
    }

    @Override
    public BookingResponse rebookCanceledBooking(Long id) {
        Booking booking = getBooking(id);
        checkRoomAvailabilityForUpdateBooking(id, booking.getRoom().getId(), booking.getCheckInDate(), booking.getCheckOutDate());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPaymentStatus(PaymentStatus.PAID);
        return bookingMapper.toResponse(bookingRepository.save(booking));
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = getBookingFromPropertyOwner(id);
        validateOwnershipForDeletion(booking);

        if(booking.getStatus().equals(BookingStatus.CONFIRMED)){
            throw new BadRequestException("Cannot delete an active Booking");
        }

        bookingRepository.delete(booking);
    }

    @Override
    public void cancelBooking(Long id) {
        Booking booking = getBooking(id);

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setPaymentStatus(PaymentStatus.REFUNDED);
        bookingRepository.save(booking);
    }

    @Override
    public BookingResponse getBookingById(Long id) {
        return bookingMapper.toResponse(getBooking(id));
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

    private void checkRoomAvailabilityForUpdateBooking(Long bookingId, Long roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        Optional<List<Booking>> existingBookings = bookingRepository.findActiveBookingsForDatesExcludingCurrent(
                roomId,
                checkInDate,
                checkOutDate,
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

    private Booking updateBooking(Booking booking, BookingRequest request, Room room, BigDecimal finalPrice) {
        booking = bookingMapper.toUpdate(booking, request);
        booking.setRoom(room);
        booking.setFinalPrice(finalPrice);
        return bookingRepository.save(booking);
    }

    private List<Guest> saveGuests(BookingRequest request, Booking savedBooking) {
        if(!CollectionUtils.isEmpty(savedBooking.getGuests())){
            guestRepository.deleteAll(savedBooking.getGuests());
        }

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

    private Booking getBooking(Long id){
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new NotFoundException("Booking not found"));

        if (isPropertyOwner(booking) || isBookingUser(booking)) {
            return booking;
        }
        throw new NotFoundException("Booking not found");
    }

    private Booking getBookingFromPropertyOwner(Long id){
        return bookingRepository.findByIdAndRoomPropertyUserId(id, UserContext.getInstance().getUser().getId())
                .orElseThrow(() -> new NotFoundException("Booking not found"));
    }

    private void validateOwnershipForDeletion(Booking booking){
        if (!booking.getRoom().getProperty().getUser().getId().equals(UserContext.getInstance().getUser().getId())) {
            throw new UnauthorizedException("You do not have permission to delete this booking");
        }
    }

    private Boolean isPropertyOwner(Booking booking){
        Long loggedUserId = UserContext.getInstance().getUser().getId();
        return booking.getRoom().getProperty().getUser().getId().equals(loggedUserId);
    }

    private Boolean isBookingUser(Booking booking){
        Long loggedUserId = UserContext.getInstance().getUser().getId();
        return booking.getUser().getId().equals(loggedUserId);
    }
}
