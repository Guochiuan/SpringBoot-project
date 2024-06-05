package edu.gatech.cs6310.repository;

import edu.gatech.cs6310.entity.Drone;
import edu.gatech.cs6310.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, Integer> {
    // select * from drones where num_orders > 0 order by num_orders desc`
    Optional<Drone> findTopByNumOrdersGreaterThanAndPilotIsNullOrderByNumOrdersDesc(int numberOrders);

    List<Drone> findAllByStoreAndRemainingCapGreaterThanEqualAndNumDeliveriesLeftGreaterThan(Store store, int weight, int deliveriesLeft);

    List<Drone> findAllByStore(Store store);
}