package ru.practicum.shareit.item;

import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping("/items")
public class ItemController {

    @PostMapping
    public ItemDto addItem(ItemDto itemDto){
        return null;
    }
    @PatchMapping("/{itemId}")
    public ItemDto editItem(@PathVariable int itemId){
        return null;
    }
    @GetMapping("/{itemId}")
    public ItemDto getItem(@PathVariable int itemId){

        return null;
    }
    @GetMapping
    public List<ItemDto> getAllItems(){
        return null;
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam(name = "text",required = false) String desc){
        return null;
    }

}
