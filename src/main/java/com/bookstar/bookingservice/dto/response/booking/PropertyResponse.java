package com.bookstar.bookingservice.dto.response.booking;

import com.bookstar.bookingservice.enums.PropertyType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PropertyResponse {
    private Long id;
    private String name;
    private PropertyType type;
    private UserResponse user;
}
