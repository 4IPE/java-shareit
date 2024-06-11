package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestInDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;


@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class RequestController {
    private final RequestClient requestClient;


    @PostMapping
    public ResponseEntity<Object> addRequest(@Valid @RequestBody ItemRequestInDto itemRequest,
                                             @RequestHeader("X-Sharer-User-Id") int userId) {
        return requestClient.addRequest(userId, itemRequest);
    }

    @GetMapping
    public ResponseEntity<Object> getRequestWithIdUser(@RequestHeader("X-Sharer-User-Id") int idRequester) {
        return requestClient.getRequestWithIdUser(idRequester);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRequest(@PositiveOrZero @RequestParam(required = false) Integer from,
                                                @Positive @RequestParam(required = false) Integer size,
                                                @RequestHeader("X-Sharer-User-Id") int idRequester) {

        return requestClient.getAllRequest(idRequester, from, size);

    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getRequestWithId(@RequestHeader("X-Sharer-User-Id") int userId,
                                                   @PathVariable int requestId) {
        return requestClient.getRequestWithId(userId, requestId);
    }
}
