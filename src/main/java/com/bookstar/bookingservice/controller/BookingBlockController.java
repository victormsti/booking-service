package com.bookstar.bookingservice.controller;

import com.bookstar.bookingservice.dto.request.bookingBlock.BookingBlockRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.service.contract.BookingBlockService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/bookings/blocks")
public class BookingBlockController {

    private final BookingBlockService bookingBlockService;

    public BookingBlockController(BookingBlockService bookingBlockService) {
        this.bookingBlockService = bookingBlockService;
    }

    @PostMapping
    public ResponseEntity<BookingResponse> createBookingBlock(@RequestBody @Valid BookingBlockRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingBlockService.createBookingBlock(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long id, @RequestBody @Valid BookingBlockRequest request){
        return ResponseEntity.ok(bookingBlockService.updateBookingBlock(id, request));
    }
}
