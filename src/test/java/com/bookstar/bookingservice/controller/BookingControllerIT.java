package com.bookstar.bookingservice.controller;

import com.bookstar.bookingservice.base.AbstractTest;
import com.bookstar.bookingservice.repository.BookingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class BookingControllerIT extends AbstractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BookingRepository bookingRepository;

    private String jwtToken;

    @BeforeEach
    void setUp() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(validAuthRequest);

        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        jwtToken = objectMapper.readTree(result.getResponse().getContentAsString()).get("token").asText();

        bookingRepository.deleteAll();
    }

    @Test
    public void whenCallMethodCreateBookingThenReturnCreatedStatusWithCorrectResponse() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(validBookingRequest);

        mockMvc.perform(post("/api/v1/bookings")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void whenCallingMethodCreateBookingThenThrowsConflictExceptionDueToOptimisticLock() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(validBookingNewYearRequest);

        Supplier<ResultActions> requestSupplier = () -> {
            try {
                return mockMvc.perform(post("/api/v1/bookings")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        CompletableFuture<ResultActions> future1 = CompletableFuture.supplyAsync(requestSupplier);
        CompletableFuture<ResultActions> future2 = CompletableFuture.supplyAsync(requestSupplier);

        CompletableFuture.allOf(future1, future2).join();

        int successCount = 0;
        int conflictCount = 0;

        for (CompletableFuture<ResultActions> future : List.of(future1, future2)) {
            int status = future.get().andReturn().getResponse().getStatus();

            if (status == HttpStatus.CREATED.value()) successCount++;
            else if (status == HttpStatus.CONFLICT.value()) conflictCount++;
        }

        assertThat(successCount).isEqualTo(1);
        assertThat(conflictCount).isEqualTo(1);
    }

    @Test
    public void whenCallMethodUpdateBookingThenReturnOkStatusWithCorrectResponse() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(validUpdateBookingBlockRequest);

        mockMvc.perform(put("/api/v1/bookings/5")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void whenCallMethodRebookCanceledBookingThenReturnOkStatusWithCorrectResponse() throws Exception {

        mockMvc.perform(put("/api/v1/bookings/2/rebook")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void whenCallMethodCancelBookingThenReturnOkStatusWithCorrectResponse() throws Exception {

        mockMvc.perform(put("/api/v1/bookings/cancellations/4")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void whenCallMethodGetBookingByIdThenReturnOkStatusWithCorrectResponse() throws Exception {

        mockMvc.perform(get("/api/v1/bookings/4")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    public void whenCallMethodGetAllBookingsThenReturnOkStatusWithCorrectResponse() throws Exception {
        mockMvc.perform(get("/api/v1/bookings")
                        .param("page", "0")
                        .param("size", "10")
                        .header("Authorization", "Bearer " + jwtToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].id").exists());
    }
}
