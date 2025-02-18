package com.bookstar.bookingservice.configuration.context;

import com.bookstar.bookingservice.model.User;
import lombok.Getter;

public class UserContext {

    @Getter
    private static UserContext instance = new UserContext();

    ThreadLocal<User> globalUser = new ThreadLocal<User>();


    private UserContext() {
    }

    public User getUser() {
        return globalUser.get();
    }

    public void setUser(User user) {
        globalUser.set(user);
    }
}
