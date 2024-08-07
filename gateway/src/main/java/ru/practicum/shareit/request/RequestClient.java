package ru.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.shareit.client.BaseClient;
import ru.practicum.shareit.request.dto.ItemRequestInDto;

import java.util.Map;

@Service
public class RequestClient extends BaseClient {
    private static final String API_PREFIX = "/requests";

    @Autowired
    public RequestClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> addRequest(Integer userId, ItemRequestInDto requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<Object> getRequestWithId(Integer userId, Integer requestId) {
        return get("/" + requestId, userId);
    }

    public ResponseEntity<Object> getAllRequest(Integer userId, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from != null ? from : 0,
                "size", size != null ? size : 10
        );
        return get("/all?from={from}&size={size}", userId, parameters);
    }

    public ResponseEntity<Object> getRequestWithIdUser(Integer userId) {
        return get("", userId);
    }


}
