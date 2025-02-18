package com.bookstar.bookingservice.builder;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsBuilder {

    public static UserDetails buildUserDetails(){
        return User.withUsername("bestuser")
                .password("bestpassword")
                .roles("USER")
                .build();
    }
}
