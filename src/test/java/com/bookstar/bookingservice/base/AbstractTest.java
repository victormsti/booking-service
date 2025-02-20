package com.bookstar.bookingservice.base;

import com.bookstar.bookingservice.builder.AuthRequestBuilder;
import com.bookstar.bookingservice.builder.BookingBlockBuilder;
import com.bookstar.bookingservice.builder.BookingBuilder;
import com.bookstar.bookingservice.builder.RoomBuilder;
import com.bookstar.bookingservice.builder.UserBuilder;
import com.bookstar.bookingservice.builder.UserDetailsBuilder;
import com.bookstar.bookingservice.dto.request.booking.BookingRequest;
import com.bookstar.bookingservice.dto.request.bookingBlock.BookingBlockRequest;
import com.bookstar.bookingservice.dto.request.token.AuthRequest;
import com.bookstar.bookingservice.dto.response.booking.BookingResponse;
import com.bookstar.bookingservice.dto.response.booking.RoomResponse;
import com.bookstar.bookingservice.model.Booking;
import com.bookstar.bookingservice.model.Room;
import com.bookstar.bookingservice.model.User;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class AbstractTest {

    protected final String expectedUsername = "bestuser";
    protected final String expectedPassword = "bestpassword";
    protected final String expectedValidToken = "validToken";


    protected final UserDetails expectedUserDetails = UserDetailsBuilder.buildUserDetails();
    protected final AuthRequest validAuthRequest = AuthRequestBuilder.buildValidAuthRequest();

    protected final AuthRequest validPropertyOwnerAuthRequest = AuthRequestBuilder.buildValidPropertyOwnerAuthRequest();
    protected final AuthRequest invalidAuthRequest = AuthRequestBuilder.buildInvalidAuthRequest();

    protected final BookingResponse expectedBookingResponse = BookingBuilder.buildBookingResponse();
    protected final Booking expectedBooking = BookingBuilder.buildBooking();
    protected final Booking expectedCanceledBooking = BookingBuilder.buildCanceledBooking();
    protected final Room expectedRoom = RoomBuilder.buildRoom();
    protected final RoomResponse expectedRoomResponse = RoomBuilder.buildRoomResponse();
    protected final User expectedUser = UserBuilder.buildGuestUser();
    protected final BookingRequest validBookingRequest = BookingBuilder.buildBookingRequest();
    protected final BookingRequest validUpdateBookingRequest = BookingBuilder.buildUpdateBookingRequest();
    protected final BookingBlockRequest validBookingBlockRequest = BookingBlockBuilder.buildValidBookingBlockRequest();
}
