package ru.practicum.shareit.booking;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.dto.BookingOutDto;
import ru.practicum.shareit.booking.enumarated.StatusBooking;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc
public class BookingControllerTest {

    @MockBean
    RequestService requestService;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

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
                .end(LocalDateTime.now())
                .start(LocalDateTime.now())
                .item(itemDto)
                .build();

    }
}
