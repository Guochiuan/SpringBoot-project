package edu.gatech.cs6310.service;

import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StoreService {
    StoreRepository storeRepository;

    public List<Store> findAll() {
        return storeRepository.findAll();
    }

    public Store findById(int id) {
        Optional<Store> result = storeRepository.findById(id);

        if(result.isPresent()) {
            return result.get();
        }
        else {
            throw new RuntimeException("Product id not found - " + id);
        }
    }

    public void save(Store category) {
        storeRepository.save(category);
    }

    public void deleteById(int id) {
        storeRepository.deleteById(id);
    }

    public Store findByManagerId(int managerId){
        for( Store store : storeRepository.findAll()) {
            if(store.getManager().getId()==managerId){
                return store;
            }

        }
        throw new RuntimeException("No store is connected to the Manager ID  " );
    }
}
