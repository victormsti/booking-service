package com.bookstar.bookingservice.repository;

import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.enums.RoomType;
import com.bookstar.bookingservice.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
            "AND (b.status = 'CONFIRMED' OR b.status = 'BLOCKED') " +
            "AND (b.checkInDate < :checkOutDate " +
            "AND b.checkOutDate > :checkInDate)")
    Optional<List<Booking>> findActiveBookingsForDates(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);

    @Query("SELECT b FROM Booking b WHERE b.room.id = :roomId " +
            "AND (b.status = 'CONFIRMED' OR b.status = 'BLOCKED') " +
            "AND (b.checkInDate < :checkOutDate " +
            "AND b.checkOutDate > :checkInDate) " +
            "AND b.id != :updatedBookingId")
    Optional<List<Booking>> findActiveBookingsForDatesExcludingCurrent(Long roomId, LocalDate checkInDate, LocalDate checkOutDate, Long updatedBookingId);


    Optional<Booking> findByIdAndUserId(Long id, Long userId);

    Optional<Booking> findByIdAndRoomPropertyUserId(Long id, Long userId);

    Optional<Booking> findByIdAndUserIdAndStatus(Long id, Long userId, BookingStatus bookingStatus);

    Optional<List<Booking>> findAllByUserId(Long userId);

    @Query("SELECT b FROM Booking b " +
            "WHERE (:userId IS NULL OR b.user.id = :userId OR b.room.property.user.id = :userId) " +
            "AND (:propertyName IS NULL OR LOWER(b.room.property.name) LIKE LOWER(CONCAT('%', :propertyName, '%'))) " +
            "AND (:roomName IS NULL OR LOWER(b.room.name) LIKE LOWER(CONCAT('%', :roomName, '%'))) " +
            "AND (:propertyType IS NULL OR b.room.property.type = :propertyType) " +
            "AND (:roomType IS NULL OR b.room.type = :roomType) " +
            "AND (:availability IS NULL OR b.status = :availability)")
    Page<Booking> findBookings(
            @Param("userId") Long userId,
            @Param("propertyName") String propertyName,
            @Param("roomName") String roomName,
            @Param("propertyType") PropertyType propertyType,
            @Param("roomType") RoomType roomType,
            @Param("availability") BookingStatus availability,
            Pageable pageable
    );
}
