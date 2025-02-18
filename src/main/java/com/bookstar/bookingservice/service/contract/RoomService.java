package com.bookstar.bookingservice.service.contract;

import com.bookstar.bookingservice.dto.response.booking.RoomResponse;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.enums.RoomType;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface RoomService {
    Page<RoomResponse> findAvailableRooms(
            String propertyName, String roomName, PropertyType propertyType,
            RoomType roomType, Integer quantityOfPeople,
            LocalDate checkInDate, LocalDate checkOutDate, int page, int size);
}
