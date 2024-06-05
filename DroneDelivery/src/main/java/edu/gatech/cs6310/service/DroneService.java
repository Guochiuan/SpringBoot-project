package edu.gatech.cs6310.service;

import edu.gatech.cs6310.entity.Drone;
import edu.gatech.cs6310.entity.Store;
import edu.gatech.cs6310.repository.DroneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DroneService {
    private DroneRepository droneRepository;

    public List<Drone> findAll() {
        return droneRepository.findAll();
    }

    public List<Drone> findAllByStore(Store store) {
        return droneRepository.findAllByStore(store);
    }

    public Optional<Drone> findById(int id) {
        return droneRepository.findById(id);
    }

    public Optional<Drone> findUnpairedAndLoadedDrone() {
        return droneRepository.findTopByNumOrdersGreaterThanAndPilotIsNullOrderByNumOrdersDesc(0);
    }

    public Drone findDroneById(int id) {
        Optional<Drone> result = droneRepository.findById(id);

        if(result.isPresent()) {
            return result.get();
        }
        else {
            throw new RuntimeException("Order id not found - " + id);
        }
    }

    public void saveAndFlush(Drone drone) {
        droneRepository.saveAndFlush(drone);
    }

    public List<Drone> findDronesForOrder(int storeId, int weight) {
        var store = new Store();
        store.setId(storeId);
        return droneRepository.findAllByStoreAndRemainingCapGreaterThanEqualAndNumDeliveriesLeftGreaterThan(store, weight, 0);
    }

    public void save(Drone drone) {
        droneRepository.save(drone);
    }

    public void deleteById(int id) {
        droneRepository.deleteById(id);
    }
}
