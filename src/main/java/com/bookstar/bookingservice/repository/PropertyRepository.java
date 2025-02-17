package com.bookstar.bookingservice.repository;

import com.bookstar.bookingservice.model.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
}
