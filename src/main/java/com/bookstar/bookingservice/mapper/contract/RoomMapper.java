package com.bookstar.bookingservice.mapper.contract;

import com.bookstar.bookingservice.dto.response.booking.RoomResponse;
import com.bookstar.bookingservice.model.Room;

import java.util.List;

public interface RoomMapper {

    RoomResponse toResponse(Room room);
    List<RoomResponse> toResponse(List<Room> rooms);
}
