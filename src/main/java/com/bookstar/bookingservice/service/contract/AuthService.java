package com.bookstar.bookingservice.service.contract;

import com.bookstar.bookingservice.dto.response.token.TokenResponse;

public interface AuthService {

    TokenResponse login(String username, String password);
}
