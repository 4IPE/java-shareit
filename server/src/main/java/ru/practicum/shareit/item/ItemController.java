package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.dto.CommentOutDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemDto> addItem(@Valid @RequestBody Item item, @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return ResponseEntity.ok().body(itemService.addItem(item, idUser));
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentOutDto> addComment(@Valid @RequestBody CommentInDto comment, @PathVariable Integer itemId, @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return ResponseEntity.ok().body(itemService.addComment(comment, itemId, idUser));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> editItem(@PathVariable Integer itemId, @Valid @RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return ResponseEntity.ok().body(itemService.editItem(itemId, item, idUser));
    }


    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable Integer itemId, @RequestHeader(value = "X-Sharer-User-Id", required = false) Integer idUser) {
        return ResponseEntity.ok().body(itemService.getItem(itemId, idUser));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItemWithIdUser(@RequestHeader("X-Sharer-User-Id") Integer idUser,
                                                           @Min(value = 0) @RequestParam(required = false, defaultValue = "0") Integer from,
                                                           @Min(value = 1) @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(itemService.getItemWithIdUser(idUser, from, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestParam(name = "text", required = false) String desc,
                                                @Min(value = 0) @RequestParam(required = false, defaultValue = "0") Integer from,
                                                @Min(value = 1) @RequestParam(required = false, defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(itemService.search(desc, from, size));
    }

}
