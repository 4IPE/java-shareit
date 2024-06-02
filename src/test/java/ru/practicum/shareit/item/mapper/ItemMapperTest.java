package ru.practicum.shareit.item.mapper;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@RequiredArgsConstructor(onConstructor_ =@Autowired)
public class ItemMapperTest {

    private final ItemMapper itemMapper;

    @Test
    void toItemDtoTest() {
        User user = new User();
        user.setId(1);
        user.setName("Dan");
        user.setEmail("ema@m.ru");
        Item item = new Item();
        item.setId(1);
        item.setName("name");
        item.setDescription("name des");
        item.setAvailable(true);
        item.setOwnerId(user);
        var itemDto = itemMapper.toItemDto(item);
        assertThat(itemDto).isNotNull();
        assertThat(itemDto.getId()).isEqualTo(item.getId());
        assertThat(itemDto.getName()).isEqualTo(item.getName());
        assertThat(itemDto.getDescription()).isEqualTo(item.getDescription());
        assertThat(itemDto.getOwnerId().getId()).isEqualTo(user.getId());
        assertThat(itemDto.getAvailable()).isEqualTo(item.getAvailable());
    }
    @Test
    void toRequestItemDtoTest(){
        User user = new User();
        user.setId(1);
        user.setName("Dan");
        user.setEmail("ema@m.ru");
        Item item = new Item();
        item.setId(1);
        item.setName("name");
        item.setDescription("name des");
        item.setAvailable(true);
        item.setOwnerId(user);
        item.setRequestId(1);
        var itemDto = itemMapper.toRequestItemDto(item);
        assertThat(itemDto).isNotNull();
        assertThat(itemDto.getId()).isEqualTo(item.getId());
        assertThat(itemDto.getName()).isEqualTo(item.getName());
        assertThat(itemDto.getDescription()).isEqualTo(item.getDescription());
        assertThat(itemDto.getRequestId()).isEqualTo(item.getRequestId());
        assertThat(itemDto.getAvailable()).isEqualTo(item.getAvailable());
    }

}
