package edu.gatech.cs6310.repository;

import edu.gatech.cs6310.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findCustomerByAccountName(String accountName);
}
