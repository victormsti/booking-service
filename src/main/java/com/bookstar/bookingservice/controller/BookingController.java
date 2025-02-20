package com.bookstar.bookingservice.controller;

import com.bookstar.bookingservice.dto.request.booking.BookingRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.enums.RoomType;
import com.bookstar.bookingservice.service.contract.BookingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody @Valid BookingRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long id, @RequestBody @Valid BookingRequest request){
        return ResponseEntity.ok(bookingService.updateBooking(id, request));
    }

    @PutMapping("{id}/rebook")
    public ResponseEntity<BookingResponse> rebookCanceledBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.rebookCanceledBooking(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("cancellations/{id}")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping
    public ResponseEntity<Page<BookingResponse>> getAllBookings(
            @RequestParam(required = false) String propertyName,
            @RequestParam(required = false) String roomName,
            @RequestParam(required = false) PropertyType propertyType,
            @RequestParam(required = false) RoomType roomType,
            @RequestParam(required = false) BookingStatus availability,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(bookingService.getAllBookings(
                propertyName, roomName, propertyType, roomType, availability, page, size)
        );
    }
}
