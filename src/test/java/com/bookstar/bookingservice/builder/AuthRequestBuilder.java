package com.bookstar.bookingservice.builder;

import com.bookstar.bookingservice.dto.request.token.AuthRequest;

public class AuthRequestBuilder {

    public static AuthRequest buildValidAuthRequest(){
        return AuthRequest.builder()
                .username("test_user")
                .password("123456")
                .build();
    }

    public static AuthRequest buildValidPropertyOwnerAuthRequest(){
        return AuthRequest.builder()
                .username("property_admin")
                .password("admin")
                .build();
    }

    public static AuthRequest buildInvalidAuthRequest(){
        return AuthRequest.builder()
                .username("foo")
                .password("11111")
                .build();
    }
}
