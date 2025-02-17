package com.bookstar.bookingservice.dto.response;

import lombok.Builder;
import lombok.Setter;

@Builder
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
}
