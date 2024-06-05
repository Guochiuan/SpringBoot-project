package edu.gatech.cs6310.repository;

import edu.gatech.cs6310.entity.Item;
import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.entity.StoreItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreItemRepository extends JpaRepository<StoreItem, Integer> {
    List<StoreItem> findByStore(Store store);
    Optional<StoreItem> findByItem(Item item);
}
