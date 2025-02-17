package com.bookstar.bookingservice.mapper.impl;

import com.bookstar.bookingservice.dto.response.RoomResponse;
import com.bookstar.bookingservice.mapper.contract.PropertyMapper;
import com.bookstar.bookingservice.mapper.contract.RoomMapper;
import com.bookstar.bookingservice.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomMapperImpl implements RoomMapper {

    private final PropertyMapper propertyMapper;

    public RoomMapperImpl(PropertyMapper propertyMapper) {
        this.propertyMapper = propertyMapper;
    }

    @Override
    public RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .type(room.getType())
                .capacity(room.getCapacity())
                .property(propertyMapper.toResponse(room.getProperty()))
                .build();
    }

    @Override
    public List<RoomResponse> toResponse(List<Room> rooms) {
        List<RoomResponse> responseList = new ArrayList<>();

        rooms.forEach(room -> {
            responseList.add(toResponse(room));
        });
        return responseList;
    }
}
