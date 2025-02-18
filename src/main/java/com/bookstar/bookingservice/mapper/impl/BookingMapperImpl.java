package com.bookstar.bookingservice.mapper.impl;

import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.dto.request.booking.BookingRequest;
import com.bookstar.bookingservice.dto.request.bookingBlock.BookingBlockRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.BookingType;
import com.bookstar.bookingservice.enums.PaymentStatus;
import com.bookstar.bookingservice.mapper.contract.BookingMapper;
import com.bookstar.bookingservice.mapper.contract.GuestMapper;
import com.bookstar.bookingservice.mapper.contract.RoomMapper;
import com.bookstar.bookingservice.mapper.contract.UserMapper;
import com.bookstar.bookingservice.model.Booking;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingMapperImpl implements BookingMapper {

    private final UserMapper userMapper;
    private final RoomMapper roomMapper;
    private final GuestMapper guestMapper;

    public BookingMapperImpl(UserMapper userMapper,
                             RoomMapper roomMapper,
                             GuestMapper guestMapper) {
        this.userMapper = userMapper;
        this.roomMapper = roomMapper;
        this.guestMapper = guestMapper;
    }

    @Override
    public BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .type(booking.getType())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .paymentStatus(booking.getPaymentStatus())
                .status(booking.getStatus())
                .finalPrice(booking.getFinalPrice())
                .quantityOfPeople(booking.getQuantityOfPeople())
                .user(userMapper.toResponse(booking.getUser()))
                .room(roomMapper.toResponse(booking.getRoom()))
                .guests(guestMapper.toResponse(booking.getGuests()))
                .build();
    }

    @Override
    public Booking toUpdate(Booking booking, BookingRequest request) {
        booking.setUser(UserContext.getInstance().getUser());
        booking.setCheckInDate(!booking.getCheckInDate().equals(request.getCheckInDate()) ? request.getCheckInDate() : booking.getCheckInDate());
        booking.setCheckOutDate(!booking.getCheckOutDate().equals(request.getCheckOutDate()) ? request.getCheckOutDate() : booking.getCheckOutDate());
        booking.setQuantityOfPeople(!booking.getQuantityOfPeople().equals(request.getQuantityOfPeople()) ? request.getQuantityOfPeople() : booking.getQuantityOfPeople());
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPaymentStatus(PaymentStatus.PAID);
        booking.setType(BookingType.GUEST);
        return booking;
    }

    @Override
    public Booking toUpdate(Booking booking, BookingBlockRequest request) {
        booking.setUser(UserContext.getInstance().getUser());
        booking.setCheckInDate(!booking.getCheckInDate().equals(request.getCheckInDate()) ? request.getCheckInDate() : booking.getCheckInDate());
        booking.setCheckOutDate(!booking.getCheckOutDate().equals(request.getCheckOutDate()) ? request.getCheckOutDate() : booking.getCheckOutDate());
        booking.setStatus(BookingStatus.BLOCKED);
        booking.setType(BookingType.BLOCK);
        return booking;
    }

    @Override
    public List<BookingResponse> toResponse(List<Booking> bookings) {
        List<BookingResponse> resultList = new ArrayList<>();

        bookings.forEach(booking -> {
            resultList.add(toResponse(booking));
        });
        return resultList;
    }
}
