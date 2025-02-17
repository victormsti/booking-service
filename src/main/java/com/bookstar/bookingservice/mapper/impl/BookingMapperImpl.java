package com.bookstar.bookingservice.mapper.impl;

import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.mapper.contract.BookingMapper;
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

    public BookingMapperImpl(UserMapper userMapper, RoomMapper roomMapper) {
        this.userMapper = userMapper;
        this.roomMapper = roomMapper;
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
                .build();
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
