package com.bookstar.bookingservice.dto.response;

import com.bookstar.bookingservice.enums.RoomType;

public class RoomResponse {

    private Long id;

    private PropertyResponse property;
    private String name;
    private RoomType type;
    private Integer capacity;
}
