package com.bookstar.bookingservice.mapper.impl;

import com.bookstar.bookingservice.dto.response.booking.UserResponse;
import com.bookstar.bookingservice.mapper.contract.UserMapper;
import com.bookstar.bookingservice.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @Override
    public List<UserResponse> toResponse(List<User> users) {
        List<UserResponse> responseList = new ArrayList<>();

        users.forEach(user -> {
            responseList.add(toResponse(user));
        });
        return responseList;
    }
}
