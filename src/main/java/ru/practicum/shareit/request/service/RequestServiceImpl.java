package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.model.NotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestInDto;
import ru.practicum.shareit.request.dto.ItemRequestOutDto;
import ru.practicum.shareit.request.mapper.RequestMapper;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    public ItemRequestOutDto addRequest(ItemRequestInDto itemRequestInDto, Integer idUser) {
        User requester = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException(User.class, idUser));
        ItemRequest itemRequest = requestMapper.toItemRequest(itemRequestInDto);
        itemRequest.setRequestor(requester);
        itemRequest.setCreated(LocalDateTime.now());
        return requestMapper.toItemRequestOutDto(requestRepository.save(itemRequest));
    }

    public List<ItemRequestOutDto> getRequestWithIdUser(int idUser) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException(User.class, idUser));
        List<ItemRequestOutDto> requests = requestRepository.findByRequestor_id(idUser).stream()
                .map(requestMapper::toItemRequestOutDto)
                .collect(Collectors.toList());
        List<ItemRequestOutDto> reques = requests;
        requests.forEach(request -> request.setItems(itemRepository.findByRequestId(request.getId()).stream()
                .map(itemMapper::toRequestItemDto)
                .collect(Collectors.toList())));
        return requests;
    }

    public ItemRequestOutDto getRequestWithId(int idUser, int requestId) {
        User user = userRepository.findById(idUser).orElseThrow(() -> new NotFoundException(User.class, idUser));
        ItemRequest itemRequest = requestRepository.findById(requestId).orElseThrow(() -> new NotFoundException(ItemRequest.class, requestId));
        List<Item> items = itemRepository.findByRequestId(itemRequest.getId());
        ItemRequestOutDto itemRequestOutDto = requestMapper.toItemRequestOutDto(itemRequest);
        itemRequestOutDto.setItems(items.stream().map(itemMapper::toRequestItemDto).collect(Collectors.toList()));
        return itemRequestOutDto;
    }

    public List<ItemRequestOutDto> getAllRequest(Integer from, Integer size, Integer idRequester) {
        if (from == null || size == null) {
            return new ArrayList<>();
        }
        User user = userRepository.findById(idRequester).orElseThrow(() -> new NotFoundException(User.class, idRequester));
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("created")));
        List<ItemRequestOutDto> requests = requestRepository.findAll(pageable).stream().filter(req -> req.getRequestor().getId() != idRequester).map(requestMapper::toItemRequestOutDto).collect(Collectors.toList());
        requests.forEach(request -> request.setItems(itemRepository.findByRequestId(request.getId()).stream().map(itemMapper::toRequestItemDto).collect(Collectors.toList())));
        return requests;
    }

}
