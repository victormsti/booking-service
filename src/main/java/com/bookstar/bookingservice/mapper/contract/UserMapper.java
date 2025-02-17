package com.bookstar.bookingservice.mapper.contract;

import com.bookstar.bookingservice.dto.response.UserResponse;
import com.bookstar.bookingservice.model.User;

import java.util.List;

public interface UserMapper {

    UserResponse toResponse(User user);
    List<UserResponse> toResponse(List<User> users);
}
