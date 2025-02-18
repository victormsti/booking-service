package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.request.token.AuthRequest;

public class AuthRequestBuilder {

    public static AuthRequest buildValidAuthRequest(){
        return AuthRequest.builder()
                .username("bestuser")
                .password("bestpassword")
                .build();
    }
}
