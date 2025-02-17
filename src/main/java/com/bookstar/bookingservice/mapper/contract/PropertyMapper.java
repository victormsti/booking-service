package com.bookstar.bookingservice.mapper.contract;

import com.bookstar.bookingservice.dto.response.booking.PropertyResponse;
import com.bookstar.bookingservice.model.Property;

import java.util.List;

public interface PropertyMapper {

    PropertyResponse toResponse(Property property);
    List<PropertyResponse> toResponse(List<Property> properties);
}
