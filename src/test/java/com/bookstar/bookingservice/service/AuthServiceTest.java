package com.bookstar.bookingservice.service;

import com.bookstar.bookingservice.base.AbstractTest;
import com.bookstar.bookingservice.configuration.security.JwtUtil;
import com.bookstar.bookingservice.dto.response.token.TokenResponse;
import com.bookstar.bookingservice.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthServiceTest extends AbstractTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void whenCallingMethodLogin_ThenShouldReturnValidToken(){
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken(expectedUserDetails, null, expectedUserDetails.getAuthorities()));
        when(userDetailsService.loadUserByUsername(expectedUsername)).thenReturn(expectedUserDetails);
        when(jwtUtil.generateToken(expectedUsername)).thenReturn(expectedValidToken);

        TokenResponse result = authService.login(expectedUsername, expectedPassword);

        assertEquals(expectedValidToken, result.getToken());
    }
}
