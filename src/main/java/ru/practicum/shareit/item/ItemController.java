package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.dto.CommentOutDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
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
    public ResponseEntity<CommentOutDto> addComment(@Valid @RequestBody CommentInDto comment, @PathVariable int itemId,@RequestHeader("X-Sharer-User-Id") Integer idUser){
        return ResponseEntity.ok().body(itemService.addComment(comment, itemId,idUser));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> editItem(@PathVariable int itemId, @Valid @RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") int idUser) {
        return ResponseEntity.ok().body(itemService.editItem(itemId, item, idUser));
    }


    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable int itemId,@RequestHeader(value = "X-Sharer-User-Id",required = false) int idUser) {
        return ResponseEntity.ok().body(itemService.getItem(itemId,idUser));
    }

    @GetMapping
    public ResponseEntity<List<ItemDto>> getItemWithIdUser(@RequestHeader("X-Sharer-User-Id") int idUser) {
        return ResponseEntity.ok().body(itemService.getItemWithIdUser(idUser));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestParam(name = "text", required = false) String desc) {
        return ResponseEntity.ok().body(itemService.search(desc));
    }

}
