package com.bookstar.bookingservice.repository;

import com.bookstar.bookingservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
