package edu.gatech.cs6310.repository;

import edu.gatech.cs6310.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findAllByStoreName(String StoreName);

    Item findByName(String name);
}
