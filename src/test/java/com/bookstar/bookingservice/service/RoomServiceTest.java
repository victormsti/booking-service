package com.bookstar.bookingservice.service;

import com.bookstar.bookingservice.base.AbstractTest;
import com.bookstar.bookingservice.configuration.context.UserContext;
import com.bookstar.bookingservice.dto.response.booking.RoomResponse;
import com.bookstar.bookingservice.enums.PropertyType;
import com.bookstar.bookingservice.enums.RoomType;
import com.bookstar.bookingservice.mapper.contract.RoomMapper;
import com.bookstar.bookingservice.model.Room;
import com.bookstar.bookingservice.repository.RoomRepository;
import com.bookstar.bookingservice.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RoomServiceTest extends AbstractTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    @Test
    void whenCallingMethodFindAvailableRooms_thenItShouldReturnASuccessfulResponse() {
        UserContext.getInstance().setUser(expectedUser);
        Page<Room> roomPage = new PageImpl<>(List.of(expectedRoom));

        when(roomRepository.findAvailableRooms(anyString(), anyString(), any(PropertyType.class),
                any(RoomType.class), anyInt(), any(LocalDate.class), any(LocalDate.class), any(Pageable.class)))
                .thenReturn(roomPage);

        Page<RoomResponse> result = roomService.findAvailableRooms("test", "test",
                PropertyType.HOTEL, RoomType.DOUBLE, 2, LocalDate.of(2025, 4, 18),
                LocalDate.of(2025, 4, 22), 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }
}
