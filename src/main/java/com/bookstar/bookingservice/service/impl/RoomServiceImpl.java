package com.bookstar.bookingservice.service.impl;

import com.bookstar.bookingservice.dto.response.booking.RoomResponse;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.enums.RoomType;
import com.bookstar.bookingservice.mapper.contract.RoomMapper;
import com.bookstar.bookingservice.model.Room;
import com.bookstar.bookingservice.repository.RoomRepository;
import com.bookstar.bookingservice.service.contract.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository,
                           RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public Page<RoomResponse> findAvailableRooms(
            String propertyName, String roomName, PropertyType propertyType,
            RoomType roomType, Integer quantityOfPeople,
            LocalDate checkInDate, LocalDate checkOutDate, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Room> rooms = roomRepository.findAvailableRooms(
                propertyName, roomName, propertyType, roomType, quantityOfPeople,
                checkInDate, checkOutDate, pageable);

        return rooms.map(roomMapper::toResponse);
    }
}
