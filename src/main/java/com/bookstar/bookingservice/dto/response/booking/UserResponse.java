package com.bookstar.bookingservice.dto.response.booking;

import com.bookstar.bookingservice.enums.UserType;
import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private UserType type;
}
