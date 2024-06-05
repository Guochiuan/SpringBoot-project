package edu.gatech.cs6310.service;

import edu.gatech.cs6310.entity.Customer;
import edu.gatech.cs6310.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public void deleteById(int id) {
        customerRepository.deleteById(id);
    }

    public Optional<Customer> findCustomerByAccountName(String accountName) {
        return customerRepository.findCustomerByAccountName(accountName);
    }
}
