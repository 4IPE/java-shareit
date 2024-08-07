package ru.practicum.shareit.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.item.dto.CommentInDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemInDto;

import java.util.Map;

@Service
public class ItemClient extends BaseClient {
    private static final String API_PREFIX = "/items";

    @Autowired
    public ItemClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addItem(Integer userId, ItemInDto itemDto) {
        return post("", userId, itemDto);
    }

    public ResponseEntity<Object> addComment(Integer userId, Integer itemId, CommentInDto comment) {
        return post("/" + itemId + "/comment", userId, comment);
    }

    public ResponseEntity<Object> editItem(Integer userId, ItemDto itemDto, Integer itemId) {

        return patch("/" + itemId, userId, itemDto);
    }

    public ResponseEntity<Object> getItem(Integer userId, Integer itemId) {
        return get("/" + itemId, userId);
    }


    public ResponseEntity<Object> getItemWithIdUser(Integer userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from != null ? from : 0,
                "size", size != null ? size : 10
        );
        return get("?from={from}&size={size}", userId, parameters);
    }


    public ResponseEntity<Object> search(String desc, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "text", desc,
                "from", from != null ? from : 0,
                "size", size != null ? size : 10
        );

        return get("/search?text={text}&from={from}&size={size}", parameters);
    }
}
