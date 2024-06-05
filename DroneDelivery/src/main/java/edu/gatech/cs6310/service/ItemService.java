package edu.gatech.cs6310.service;

import edu.gatech.cs6310.entity.Item;
import edu.gatech.cs6310.repository.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ItemService {
    ItemRepository itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public List<Item> findAllByStoreName(String name) {
        return itemRepository.findAllByStoreName(name);
    }

    public Item findById(int id) {
        Optional<Item> result = itemRepository.findById(id);

        if(result.isPresent()) {
            return result.get();
        }
        else {
            throw new RuntimeException("Order id not found - " + id);
        }
    }

    public Item findByName(String name){
        return itemRepository.findByName(name);
    }

    public Item save(Item order) {
        return itemRepository.save(order);
    }

    public void deleteById(int id) {
        itemRepository.deleteById(id);
    }
}
