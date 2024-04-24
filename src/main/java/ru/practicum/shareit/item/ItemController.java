package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
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
public class ItemController {
    private final ItemService itemService;


    @PostMapping
    public ResponseEntity<ItemDto> addItem(@Valid @RequestBody Item item, @RequestHeader("X-Sharer-User-Id") Integer idUser) {
        return ResponseEntity.ok().body(itemService.addItem(item, idUser));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> editItem(@PathVariable int itemId, @Valid @RequestBody ItemDto item, @RequestHeader("X-Sharer-User-Id") int idUser) {
        return ResponseEntity.ok().body(itemService.editItem(itemId, item, idUser)) ;
    }


    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDto> getItem(@PathVariable int itemId) {
        return ResponseEntity.ok().body(itemService.getItem(itemId));
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
