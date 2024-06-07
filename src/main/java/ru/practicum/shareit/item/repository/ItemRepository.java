package ru.practicum.shareit.item.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Page<Item> findByOwnerId_id(int id, Pageable pageable);

    List<Item> findByOwnerId_id(int id);

    List<Item> findByRequestId(int id);
}
