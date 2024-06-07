package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.RequestItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {CommentMapper.class, BookingMapper.class, UserMapper.class})
public interface ItemMapper {
    @Mapping(target = "nextBooking", ignore = true)
    @Mapping(target = "lastBooking", ignore = true)
    @Mapping(target = "requestId", source = "request.id")
    ItemDto toItemDto(Item item);

    @Mapping(target = "requestId", source = "request.id")
    RequestItemDto toRequestItemDto(Item item);
}
