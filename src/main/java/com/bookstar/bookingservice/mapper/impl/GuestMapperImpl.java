package com.bookstar.bookingservice.mapper.impl;

import com.bookstar.bookingservice.dto.response.booking.GuestResponse;
import com.bookstar.bookingservice.mapper.contract.GuestMapper;
import com.bookstar.bookingservice.model.Guest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuestMapperImpl implements GuestMapper {

    @Override
    public GuestResponse toResponse(Guest guest) {
        return GuestResponse.builder()
                .id(guest.getId())
                .firstName(guest.getFirstName())
                .lastName(guest.getLastName())
                .birthDate(guest.getBirthDate())
                .mainGuest(guest.getMainGuest())
                .build();
    }

    @Override
    public List<GuestResponse> toResponse(List<Guest> guests) {
        List<GuestResponse> responseList = new ArrayList<>();

        guests.forEach(guest -> {
            responseList.add(toResponse(guest));
        });
        return responseList;
    }
}
