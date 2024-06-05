package edu.gatech.cs6310.service;

import edu.gatech.cs6310.entity.Item;
import edu.gatech.cs6310.entity.Pilot;
import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.entity.StoreItem;
import edu.gatech.cs6310.repository.PilotRepository;
import edu.gatech.cs6310.repository.StoreItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreItemService {
    private StoreItemRepository storeItemRepository;

    public Optional<StoreItem> findById(int id) {
        return storeItemRepository.findById(id);
    }

    public List<StoreItem> findByStore(Store store) {
        return storeItemRepository.findByStore(store);
    }

    public void save(StoreItem storeItem) {
        storeItemRepository.save(storeItem);
    }

    public Optional<StoreItem> findByItem(Item item) {
        return storeItemRepository.findByItem(item);
    }

    public void deleteById(int id) {
        storeItemRepository.deleteById(id);
    }
}
