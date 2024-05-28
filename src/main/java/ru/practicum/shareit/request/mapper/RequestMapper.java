package ru.practicum.shareit.request.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.dto.ItemRequestInDto;
import ru.practicum.shareit.request.dto.ItemRequestOutDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {ItemMapper.class})
public interface RequestMapper {
    @Mapping(target = "requestor",ignore = true)
    @Mapping(target = "created",ignore = true)
    ItemRequest toItemRequest(ItemRequestInDto iri);
    ItemRequestOutDto toItemRequestOutDto (ItemRequest ir);

}
