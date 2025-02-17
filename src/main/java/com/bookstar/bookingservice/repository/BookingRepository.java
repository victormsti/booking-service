package com.bookstar.bookingservice.repository;

import com.bookstar.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
            "AND b.status = 'CONFIRMED' " +
            "AND (b.check_in_date < :checkOutDate " +
            "AND b.check_out_date > :checkInDate)")
    Optional<List<Booking>> findActiveBookingsForDates(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);
}
