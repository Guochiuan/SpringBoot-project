package edu.gatech.cs6310.repository;

import edu.gatech.cs6310.entity.Customer;
import edu.gatech.cs6310.entity.Drone;
import edu.gatech.cs6310.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByDrone(Drone drone);

    List<Orders> findAllByCustomer(Customer customer);
}
