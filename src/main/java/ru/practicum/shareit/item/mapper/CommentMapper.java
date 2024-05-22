package ru.practicum.shareit.item.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.dto.CommentOutDto;
import ru.practicum.shareit.user.mapper.UserMapper;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {ItemMapper.class, UserMapper.class})
public interface CommentMapper {
    @Mapping(target = "authorName", source = "comment.author.name")
    CommentOutDto toCommentOutDto(Comment comment);

    @Mapping(target = "item", ignore = true)
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "created", ignore = true)
    Comment toComment(CommentInDto comment);
}
