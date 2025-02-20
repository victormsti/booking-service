package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.response.booking.UserResponse;
import com.bookstar.bookingservice.enums.UserType;
import com.bookstar.bookingservice.model.User;

public class UserBuilder {

    public static UserResponse buildGuestUserResponse(){
        return UserResponse.builder()
                .id(1L)
                .type(UserType.CLIENT)
                .email("user@test.com")
                .username("testuser")
                .build();
    }

    public static User buildGuestUser(){
        return User.builder()
                .id(1L)
                .type(UserType.CLIENT)
                .email("user@test.com")
                .username("testuser")
                .build();
    }

    public static User buildPropertyOwnerUser(){
        return User.builder()
                .id(1L)
                .type(UserType.PROPERTY_ADMIN)
                .email("user@test.com")
                .username("testuser")
                .build();
    }
}
