package ru.practicum.shareit.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.dto.ItemRequestOutDto;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemRequestController.class)
@AutoConfigureMockMvc
public class RequestControllerTest {
    @MockBean
    RequestService requestService;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();


    private UserDto userDto;
    private ItemRequestOutDto itemRequestOutDto;


    @BeforeEach
    void setUp() {
        userDto = UserDto.builder()
                .id(1)
                .email("men@g.ru")
                .name("Jon")
                .build();
        itemRequestOutDto = new ItemRequestOutDto();
        itemRequestOutDto.setId(1);
        itemRequestOutDto.setCreated(null);
        itemRequestOutDto.setDescription("Des");
        itemRequestOutDto.setItems(Collections.emptyList());

    }

    @Test
    void saveRequestTest() throws Exception {
        when(requestService.addRequest(any(), anyInt()))
                .thenReturn(itemRequestOutDto);
        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId()))
                        .content(mapper.writeValueAsString(itemRequestOutDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemRequestOutDto.getId()), Integer.class))
                .andExpect(jsonPath("$.description", is(itemRequestOutDto.getDescription())))
                .andExpect(jsonPath("$.items", is(itemRequestOutDto.getItems())))
                .andExpect(jsonPath("$.created", is(itemRequestOutDto.getCreated())));
    }

    @Test
    void getRequestWithIdUserTest() throws Exception {
        when(requestService.getRequestWithIdUser(anyInt())).thenReturn(Collections.emptyList());

        mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

    @Test
    void getAllRequestTest() throws Exception {
        when(requestService.getAllRequest(anyInt(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


    @Test
    void getRequestWithIdTest() throws Exception {
        when(requestService.getRequestWithId(anyInt(), anyInt()))
                .thenReturn(itemRequestOutDto);
        mvc.perform(get("/requests/{requestId}", itemRequestOutDto.getId())
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemRequestOutDto.getId()), Integer.class))
                .andExpect(jsonPath("$.description", is(itemRequestOutDto.getDescription())))
                .andExpect(jsonPath("$.items", is(itemRequestOutDto.getItems())))
                .andExpect(jsonPath("$.created", is(itemRequestOutDto.getCreated())));

    }
}
