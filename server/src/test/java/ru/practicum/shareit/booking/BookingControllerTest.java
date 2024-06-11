package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.booking.service.BookingService;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = BookingController.class)
@AutoConfigureMockMvc
public class BookingControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private BookingService bookingService;
    @Autowired
    private MockMvc mvc;
    private ItemDto itemDto;
    private UserDto userDto;
    private BookingOutDto booking;

    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(1)
                .email("men@g.ru")
                .name("Jon")
                .build();
        itemDto = ItemDto.builder()
                .id(1)
                .name("Item")
                .description("Des")
                .available(true)
                .requestId(2)
                .ownerId(userDto)
                .lastBooking(null)
                .nextBooking(null)
                .comments(null)
                .build();
        booking = BookingOutDto.builder().id(1)
                .status(StatusBooking.APPROVED)
                .booker(userDto)
                .item(itemDto)
                .build();
    }

    @Test
    void saveCommentTest() throws Exception {
        when(bookingService.addBooking(any(), anyInt()))
                .thenReturn(booking);
        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId()))
                        .content(mapper.writeValueAsString(booking))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(booking.getId()), Integer.class))
                .andExpect(jsonPath("$.end", is(booking.getEnd()), LocalDateTime.class))
                .andExpect(jsonPath("$.start", is(booking.getStart()), LocalDateTime.class));
    }

    @Test
    void confirmationTest() throws Exception {
        when(bookingService.confirmation(anyInt(), anyBoolean(), anyInt())).thenReturn(booking);

        mvc.perform(patch("/bookings/{bookingId}", booking.getId())
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId()))
                        .param("approved", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(booking.getId()), Integer.class))
                .andExpect(jsonPath("$.end", is(booking.getEnd())))
                .andExpect(jsonPath("$.start", is(booking.getStart())));

    }

    @Test
    void getBookingByIdTest() throws Exception {
        when(bookingService.getBookingById(anyInt(), anyInt())).thenReturn(booking);

        mvc.perform(get("/bookings/{bookingId}", booking.getId())
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(booking.getId()), Integer.class))
                .andExpect(jsonPath("$.end", is(booking.getEnd())))
                .andExpect(jsonPath("$.start", is(booking.getStart())));

    }

    @Test
    void getAllBookingTest() throws Exception {
        when(bookingService.getAllBookingWithState(any(), anyInt(), anyInt(), anyInt())).thenReturn(Collections.emptyList());

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId()))
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void getBookingOwnerTest() throws Exception {
        when(bookingService.getBookingOwnerWithState(any(), anyInt(), anyInt(), anyInt())).thenReturn(Collections.emptyList());

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId()))
                        .param("state", "ALL")
                        .param("from", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }
}
