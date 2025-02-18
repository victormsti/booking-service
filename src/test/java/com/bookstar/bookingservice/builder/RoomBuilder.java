package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.response.booking.RoomResponse;
import com.bookstar.bookingservice.enums.RoomType;
import com.bookstar.bookingservice.model.Room;

import java.math.BigDecimal;

public class RoomBuilder {

    public static RoomResponse buildRoomResponse(){
        return RoomResponse.builder()
                .id(1L)
                .type(RoomType.DOUBLE)
                .name("Room Test")
                .pricePerNight(BigDecimal.valueOf(150))
                .property(PropertyBuilder.buildPropertyResponse())
                .build();
    }

    public static Room buildRoom(){
        return Room.builder()
                .id(1L)
                .type(RoomType.DOUBLE)
                .name("Room Test")
                .pricePerNight(BigDecimal.valueOf(150))
                .property(PropertyBuilder.buildProperty())
                .build();
    }
}
