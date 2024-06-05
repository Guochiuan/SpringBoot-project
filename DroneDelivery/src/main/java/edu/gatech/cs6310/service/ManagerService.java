package edu.gatech.cs6310.service;

import edu.gatech.cs6310.entity.Manager;
import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.repository.ManagerRepository;
import edu.gatech.cs6310.repository.StoreRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ManagerService {
    ManagerRepository managerRepository;
    StoreRepository storeRepository;

    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    public Manager findById(int id) {
        Optional<Manager> result = managerRepository.findById(id);

        if(result.isPresent()) {
            return result.get();
        }
        else {
            throw new RuntimeException("Manager id not found - " + id);
        }
    }

    public void save(Manager manager) {
        managerRepository.save(manager);
    }

    public void deleteById(int id) {
        var findManager = managerRepository.findById(id);
        if (findManager.isPresent()) {
            findManager.get().getStore().setManager(null);
            managerRepository.delete(findManager.get());
        }
    }

    public Manager findByAccount_name(String account_name) {

        for( Manager manager : managerRepository.findAll()) {
            if(manager.getAccountName().equals(account_name)){

                return manager;
            }

        }
        throw new RuntimeException("No Manager has an acccount name -" +account_name );
    }




}