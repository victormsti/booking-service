package com.bookstar.bookingservice.controller;

import com.bookstar.bookingservice.dto.response.booking.RoomResponse;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.enums.RoomType;
import com.bookstar.bookingservice.service.contract.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<Page<RoomResponse>> getAvailableRooms(
            @RequestParam(required = false) String propertyName,
            @RequestParam(required = false) String roomName,
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) RoomType roomType,
            @RequestParam(required = false) Integer quantityOfPeople,
            @RequestParam LocalDate checkInDate,
            @RequestParam LocalDate checkOutDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<RoomResponse> rooms = roomService.findAvailableRooms(
                propertyName, roomName, propertyType, roomType, quantityOfPeople,
                checkInDate, checkOutDate, page, size);

        return ResponseEntity.ok(rooms);
    }
}
