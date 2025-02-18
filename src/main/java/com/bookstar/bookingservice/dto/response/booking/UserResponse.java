package com.bookstar.bookingservice.dto.response.booking;

import com.bookstar.bookingservice.enums.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private UserType type;
}
