package ru.practicum.shareit.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.shareit.request.repository.RequestRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RequestRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    private User user;
    private ItemRequest request;
    @BeforeEach
    void setUp(){
        this.user = createUser("User","user@g.ru");
        this.request = createRequest("item des");
    }

    private User createUser(String name,String email){
        User userCreate = new User();
        userCreate.setEmail(email);
        userCreate.setName(name);
        return userRepository.save(userCreate);
    }
    private ItemRequest createRequest(String des){
        ItemRequest itemRequest = new ItemRequest();
        itemRequest.setDescription(des);
        itemRequest.setCreated(LocalDateTime.now());
        itemRequest.setRequestor(user);
        return requestRepository.save(itemRequest);
    }

    @Test
    void findByRequestor_idTest() {
        List<ItemRequest> items = requestRepository.findByRequestor_id(user.getId());
        assertThat(items).isNotEmpty().isNotNull();
        assertThat(items.get(0)).isEqualTo(request);
    }


}
