package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import ru.practicum.shareit.item.dto.RequestItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestInDto;
import ru.practicum.shareit.request.dto.ItemRequestOutDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.request.service.RequestService;
import ru.practicum.shareit.request.service.RequestServiceImpl;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    private RequestItemDto itemDto;
    private ItemRequest request;
    private ItemRequestInDto requestInDto;
    private ItemRequestOutDto requestOutDto;
    private User requestUser;


    @BeforeEach
    void setUp() {
        itemRepository = mock(ItemRepository.class);
        userRepository = mock(UserRepository.class);
        requestRepository = mock(RequestRepository.class);
        requestMapper = mock(RequestMapper.class);
        itemMapper = mock(ItemMapper.class);
        requestService = new RequestServiceImpl(requestRepository, userRepository, requestMapper, itemRepository, itemMapper);
        user = new User();
        user.setName("User");
        user.setId(1);
        user.setEmail("name@f.g");
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
        itemDto = RequestItemDto.builder()
                .id(1)
                .requestId(requestUser.getId())
                .name("Name")
                .description("Des")
                .available(true)
                .build();
        request = new ItemRequest();
        request.setId(4);
        request.setCreated(LocalDateTime.now());
        request.setDescription("des");
        request.setRequestor(requestUser);
        requestInDto = new ItemRequestInDto();
        requestInDto.setDescription("Des");
        requestOutDto = new ItemRequestOutDto();
        requestOutDto.setId(1);
        requestOutDto.setItems(Collections.emptyList());
        requestOutDto.setDescription("Des");
        requestOutDto.setCreated(LocalDateTime.now());

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

    @Test
    void addRequestTest() {
        when(userRepository.findById(requestUser.getId())).thenReturn(Optional.of(requestUser));
        when(requestMapper.toItemRequest(requestInDto)).thenReturn(request);
        when(requestRepository.save(request)).thenReturn(request);
        when(requestMapper.toItemRequestOutDto(request)).thenReturn(requestOutDto);
        var res = requestService.addRequest(requestInDto, requestUser.getId());
        assertThat(res).isNotNull();
    }

    @Test
    void getRequestWithIdTest() {
        when(userRepository.findById(requestUser.getId())).thenReturn(Optional.of(requestUser));
        when(requestRepository.findById(request.getId())).thenReturn(Optional.ofNullable(request));
        when(itemRepository.findByRequestId(request.getId())).thenReturn(List.of(item));
        when(requestMapper.toItemRequestOutDto(request)).thenReturn(requestOutDto);
        when(itemMapper.toRequestItemDto(item)).thenReturn(itemDto);
        var res = requestService.getRequestWithId(requestUser.getId(), request.getId());
        assertThat(res).isNotNull();
    }

    @Test
    void getAllRequestTest() {
        var resEmpty = requestService.getAllRequest(null, null, null);
        assertThat(resEmpty).isNotNull().isEmpty();
        when(userRepository.findById(requestUser.getId())).thenReturn(Optional.of(requestUser));
        when(requestRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Order.asc("created"))))).thenReturn(Page.empty());
        when(itemRepository.findByRequestId(request.getId())).thenReturn(List.of(item));
        when(requestMapper.toItemRequestOutDto(request)).thenReturn(requestOutDto);
        when(itemMapper.toRequestItemDto(item)).thenReturn(itemDto);
        var res = requestService.getAllRequest(0, 10, requestUser.getId());
        assertThat(res).isNotNull();
    }
}
