package com.bookstar.bookingservice.dto.response.booking;

import com.bookstar.bookingservice.enums.RoomType;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class RoomResponse {
    private Long id;
    private PropertyResponse property;
    private String name;
    private RoomType type;
    private Integer capacity;
}
