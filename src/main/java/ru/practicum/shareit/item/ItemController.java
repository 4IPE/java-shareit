package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public ItemDto addItem(Item item,@RequestHeader("X-Sharer-User-Id") int idUser) {
        return itemService.addItem(item,idUser);
    }

    @PatchMapping("/{itemId}")
    public ItemDto editItem(@PathVariable int itemId,@RequestBody ItemDto item,@RequestHeader("X-Sharer-User-Id") int idUser){
        return itemService.editItem(itemId, item,idUser);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable int itemId) {
        return itemService.getItem(itemId);
    }

    @GetMapping
    public List<ItemDto> getAllItems() {
        return itemService.getAllItem();
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam(name = "text", required = false) String desc) {
        return itemService.search(desc);
    }

}
