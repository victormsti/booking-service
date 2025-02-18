package com.bookstar.bookingservice.dto.response.booking;

import com.bookstar.bookingservice.enums.BookingStatus;
import com.bookstar.bookingservice.enums.BookingType;
import com.bookstar.bookingservice.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long id;
    private UserResponse user;

    private RoomResponse room;
    private BookingStatus status;
    private BookingType type;
    private Integer quantityOfPeople;
    private LocalDate checkInDate;

    private LocalDate checkOutDate;
    private BigDecimal finalPrice;
    private PaymentStatus paymentStatus;

    private List<GuestResponse> guests;
}
