package ru.practicum.shareit.item;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.CommentOutDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc
public class ItemControllerTest {
    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper mapper = new ObjectMapper();

    private ItemDto itemDto;
    private UserDto userDto;
    private CommentOutDto comment;

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
        comment = CommentOutDto.builder()
                .id(1)
                .text("Comment")
                .authorName(userDto.getName())
                .created(null)
                .build();
    }

    @Test
    void saveItemTest() throws Exception {
        when(itemService.addItem(any(), anyInt()))
                .thenReturn(itemDto);
        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId()))
                        .content(mapper.writeValueAsString(itemDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Integer.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.comments", is(itemDto.getComments())))
                .andExpect(jsonPath("$.nextBooking", is(itemDto.getNextBooking())))
                .andExpect(jsonPath("$.lastBooking", is(itemDto.getLastBooking())))
                .andExpect(jsonPath("$.requestId", is(itemDto.getRequestId())));
    }

    @Test
    void saveCommentTest() throws Exception {
        when(itemService.addComment(any(), anyInt(), anyInt()))
                .thenReturn(comment);
        mvc.perform(post("/items/{itemId}/comment", itemDto.getId())
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId()))
                        .content(mapper.writeValueAsString(comment))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(comment.getId()), Integer.class))
                .andExpect(jsonPath("$.text", is(comment.getText())))
                .andExpect(jsonPath("$.created", is(comment.getCreated())))
                .andExpect(jsonPath("$.authorName", is(comment.getAuthorName())));
    }

    @Test
    void getItemTest() throws Exception {
        when(itemService.getItem(anyInt(), anyInt())).thenReturn(itemDto);

        mvc.perform(get("/items/{itemId}", itemDto.getId())
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(itemDto.getId()), Integer.class))
                .andExpect(jsonPath("$.name", is(itemDto.getName())))
                .andExpect(jsonPath("$.description", is(itemDto.getDescription())))
                .andExpect(jsonPath("$.available", is(itemDto.getAvailable())))
                .andExpect(jsonPath("$.comments", is(itemDto.getComments())))
                .andExpect(jsonPath("$.nextBooking", is(itemDto.getNextBooking())))
                .andExpect(jsonPath("$.lastBooking", is(itemDto.getLastBooking())))
                .andExpect(jsonPath("$.requestId", is(itemDto.getRequestId())));

    }

    @Test
    void getItemWithIdUserTest() throws Exception {
        when(itemService.getItemWithIdUser(anyInt(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", String.valueOf(userDto.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }


    @Test
    void searchTest() throws Exception {
        when(itemService.search(anyString(), anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());
        mvc.perform(get("/items/search")
                        .param("text", itemDto.getDescription()))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

    }

}
