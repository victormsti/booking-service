package com.bookstar.bookingservice.dto.response;

import com.bookstar.bookingservice.enums.PropertyType;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class PropertyResponse {
    private Long id;
    private String name;
    private PropertyType type;
}
