package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestInDto;
import ru.practicum.shareit.request.dto.ItemRequestOutDto;
import ru.practicum.shareit.request.service.RequestService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<ItemRequestOutDto> addRequest(@Valid @RequestBody ItemRequestInDto itemRequest,
                                                        @RequestHeader("X-Sharer-User-Id") int idRequester) {
        return ResponseEntity.ok().body(requestService.addRequest(itemRequest, idRequester));
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestOutDto>> getRequestWithIdUser(@RequestHeader("X-Sharer-User-Id") int idRequester) {
        return ResponseEntity.ok().body(requestService.getRequestWithIdUser(idRequester));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestOutDto>> getAllRequest(@Min(value = 0) @RequestParam(required = false) Integer from,
                                                                 @Min(value = 1) @RequestParam(required = false) Integer size,
                                                                 @RequestHeader("X-Sharer-User-Id") int idRequester) {
        return ResponseEntity.ok().body(requestService.getAllRequest(from, size, idRequester));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestOutDto> getRequestWithId(@RequestHeader("X-Sharer-User-Id") int idRequester,
                                                              @PathVariable int requestId) {
        return ResponseEntity.ok().body(requestService.getRequestWithId(idRequester, requestId));
    }
}
