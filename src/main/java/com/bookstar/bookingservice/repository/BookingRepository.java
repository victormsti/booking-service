package com.bookstar.bookingservice.repository;

import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
            "AND b.status = 'CONFIRMED' " +
            "AND (b.checkInDate < :checkOutDate " +
            "AND b.checkOutDate > :checkInDate)")
    Optional<List<Booking>> findActiveBookingsForDates(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
            "AND b.status = 'CONFIRMED' " +
            "AND (b.checkInDate < :checkOutDate " +
            "AND b.checkOutDate > :checkInDate) " +
            "AND b.id != :updatedBookingId")
    Optional<List<Booking>> findActiveBookingsForDatesExcludingCurrent(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, Long updatedBookingId);


    Optional<Booking> findByIdAndUserId(Long id, Long userId);

    Optional<Booking> findByIdAndUserIdAndStatus(Long id, Long userId, BookingStatus bookingStatus);

    Optional<List<Booking>> findAllByUserId(Long userId);

}
