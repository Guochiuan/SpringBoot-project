package edu.gatech.cs6310.service;

import edu.gatech.cs6310.entity.Customer;
import edu.gatech.cs6310.entity.Drone;
import edu.gatech.cs6310.entity.Orders;
import edu.gatech.cs6310.repository.OrdersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrdersService {
    OrdersRepository ordersRepository;

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    public Orders findById(int id) {
        Optional<Orders> result = ordersRepository.findById(id);

        if(result.isPresent()) {
            return result.get();
        }
        else {
            throw new RuntimeException("Order id not found - " + id);
        }
    }

    public void save(Orders order) {
        ordersRepository.save(order);
    }

    public void flush() {
        ordersRepository.flush();
    }

    public void deleteById(int id) {
        ordersRepository.deleteById(id);
    }

    public List<Orders> findByDrone(Drone drone) {
        return ordersRepository.findByDrone(drone);
    }
    public List<Orders> findAllByCustomer(Customer customer) {
        return ordersRepository.findAllByCustomer(customer);
    }
}
