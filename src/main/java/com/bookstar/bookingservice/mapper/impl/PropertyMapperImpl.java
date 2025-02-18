package com.bookstar.bookingservice.mapper.impl;

import com.bookstar.bookingservice.dto.response.booking.PropertyResponse;
import com.bookstar.bookingservice.mapper.contract.PropertyMapper;
import com.bookstar.bookingservice.model.Property;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PropertyMapperImpl implements PropertyMapper {
    @Override
    public PropertyResponse toResponse(Property property) {
        return PropertyResponse.builder()
                .id(property.getId())
                .type(property.getType())
                .name(property.getName())
                .build();
    }

    @Override
    public List<PropertyResponse> toResponse(List<Property> properties) {
        List<PropertyResponse> responseList = new ArrayList<>();

        properties.forEach(property -> {
            responseList.add(toResponse(property));
        });
        return responseList;
    }
}
