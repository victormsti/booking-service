package com.bookstar.bookingservice.mapper.contract;

import com.bookstar.bookingservice.dto.response.booking.GuestResponse;
import com.bookstar.bookingservice.model.Guest;

import java.util.List;

public interface GuestMapper {

    GuestResponse toResponse(Guest guest);
    List<GuestResponse> toResponse(List<Guest> guests);
}
