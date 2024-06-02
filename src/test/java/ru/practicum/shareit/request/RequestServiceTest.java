package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestOutDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.request.service.RequestServiceImpl;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestPropertySource(properties = {"db.name=test"})
public class RequestServiceTest {
    private RequestService requestService;
    private ItemMapper itemMapper;
    private RequestMapper requestMapper;
    private UserRepository userRepository;
    private RequestRepository requestRepository;
    private ItemRepository itemRepository;


    private User user;
    private Item item;
    private ItemRequest request;
    private User requestUser;


    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        userRepository = mock(UserRepository.class);
        requestRepository = mock(RequestRepository.class);
        requestMapper = mock(RequestMapper.class);
        itemMapper = mock(ItemMapper.class);
        when(requestRepository.save(any())).thenReturn(ItemRequestOutDto.class);
        requestService = new RequestServiceImpl(requestRepository, userRepository, requestMapper, itemRepository, itemMapper);
        user = new User();
        user.setName("User");
        user.setId(1);
        user.setName("name@f.g");
        requestUser = new User();
        requestUser.setName("requestUser");
        requestUser.setId(2);
        requestUser.setName("narequestUserme@f.g");
        item = new Item();
        item.setName("Item");
        item.setId(3);
        item.setDescription("Des");
        item.setAvailable(true);
        item.setRequestId(2);
        item.setOwnerId(user);
        request = new ItemRequest();
        request.setId(4);
        request.setCreated(LocalDateTime.now());
        request.setDescription("des");
        request.setRequestor(requestUser);
    }

    @Test
    void getRequestWithIdUserTest() {
        when(userRepository.findById(requestUser.getId())).thenReturn(Optional.of(requestUser));
        when(requestRepository.findByRequestor_id(requestUser.getId())).thenReturn(Collections.emptyList());
        when(itemRepository.findByRequestId(request.getId())).thenReturn(Collections.emptyList());
        var res = requestService.getRequestWithIdUser(requestUser.getId());
        assertThat(res).isNotNull().isEmpty();
        verify(requestRepository, times(1)).findByRequestor_id(anyInt());
    }
}
