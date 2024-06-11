package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> addItem(@Valid @RequestBody ItemInDto item, @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return itemClient.addItem(idUser, item);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@Valid @RequestBody CommentInDto comment, @PathVariable int itemId, @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return itemClient.addComment(idUser, itemId, comment);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> editItem(@PathVariable int itemId, @Valid @RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") int idUser) {
        return itemClient.editItem(idUser, item, itemId);
    }


    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItem(@PathVariable int itemId, @RequestHeader(value = "X-Sharer-User-Id", required = false) int idUser) {
        return itemClient.getItem(idUser, itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getItemWithIdUser(@RequestHeader("X-Sharer-User-Id") int idUser,
                                                    @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                                    @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        return itemClient.getItemWithIdUser(idUser, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam(name = "text", required = false) String desc,
                                         @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer from,
                                         @Positive @RequestParam(required = false, defaultValue = "10") Integer size) {
        return itemClient.search(desc, from, size);
    }

}
