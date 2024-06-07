package ru.practicum.shareit.request.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.request.ItemRequest;

import java.util.List;

public interface RequestRepository extends JpaRepository<ItemRequest, Integer> {
    List<ItemRequest> findByRequestor_id(int idUser);

}
