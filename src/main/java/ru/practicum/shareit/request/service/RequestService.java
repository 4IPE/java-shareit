package ru.practicum.shareit.request.service;

import ru.practicum.shareit.request.dto.ItemRequestInDto;
import ru.practicum.shareit.request.dto.ItemRequestOutDto;

import java.util.List;

public interface RequestService {
    ItemRequestOutDto addRequest(ItemRequestInDto itemRequestInDto, Integer idUser);

    List<ItemRequestOutDto> getRequestWithIdUser(int idUser);

    ItemRequestOutDto getRequestWithId(int idUser, int requestId);

    List<ItemRequestOutDto> getAllRequest(Integer from, Integer size, Integer idRequester);
}
