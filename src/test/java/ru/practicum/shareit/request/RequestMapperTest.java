package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.request.dto.ItemRequestInDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.user.User;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestMapperTest {

    private final RequestMapper requestMapper;

    @Test
    void toItemDtoTest() {
        ItemRequestInDto iri = new ItemRequestInDto();
        iri.setDescription("DES");
        var itemRequest = requestMapper.toItemRequest(iri);
        assertThat(itemRequest).isNotNull();
        assertThat(itemRequest.getDescription()).isEqualTo(iri.getDescription());
    }

    @Test
    void toItemRequestOutDtoTest() {
        ItemRequest ir = new ItemRequest();
        ir.setDescription("DES");
        ir.setCreated(LocalDateTime.now());
        ir.setRequestor(new User());
        ir.setId(1);
        var itemRequest = requestMapper.toItemRequestOutDto(ir);
        assertThat(itemRequest).isNotNull();
        assertThat(itemRequest.getDescription()).isEqualTo(ir.getDescription());
        assertThat(itemRequest.getId()).isEqualTo(ir.getId());
        assertThat(itemRequest.getCreated()).isEqualTo(ir.getCreated());
    }
}
