package com.bookstar.bookingservice.repository;

import com.bookstar.bookingservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
