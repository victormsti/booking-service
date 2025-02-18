package com.bookstar.bookingservice.repository;

import com.bookstar.bookingservice.model.Room;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("""
        SELECT r FROM Room r
        JOIN r.property p
        WHERE (:propertyName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :propertyName, '%')))
        AND (:roomName IS NULL OR LOWER(r.name) LIKE LOWER(CONCAT('%', :roomName, '%')))
        AND (:propertyType IS NULL OR p.type = :propertyType)
        AND (:roomType IS NULL OR r.type = :roomType)
        AND (:quantityOfPeople IS NULL OR r.capacity >= :quantityOfPeople)
        AND NOT EXISTS (
            SELECT b FROM Booking b 
            WHERE b.room = r 
            AND (b.status = 'CONFIRMED' OR b.status = 'BLOCKED')
            AND (b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate)
        )
    """)
    Page<Room> findAvailableRooms(
            String propertyName, String roomName, PropertyType propertyType,
            RoomType roomType, Integer quantityOfPeople,
            LocalDate checkInDate, LocalDate checkOutDate, Pageable pageable);
}
