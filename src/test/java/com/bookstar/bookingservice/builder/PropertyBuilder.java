package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.response.booking.PropertyResponse;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.model.Property;

public class PropertyBuilder {

    public static PropertyResponse buildPropertyResponse(){
        return PropertyResponse.builder()
                .id(1L)
                .user(UserBuilder.buildGuestUserResponse())
                .type(PropertyType.HOTEL)
                .name("Property Test")
                .build();
    }

    public static Property buildProperty(){
        return Property.builder()
                .id(1L)
                .user(UserBuilder.buildPropertyOwnerUser())
                .type(PropertyType.HOTEL)
                .name("Property Test")
                .build();
    }
}
