package edu.gatech.cs6310.controller;

import edu.gatech.cs6310.entity.Customer;
import edu.gatech.cs6310.service.CustomerService;
import edu.gatech.cs6310.service.StoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/customers")
@AllArgsConstructor
public class CustomerController {
    private CustomerService customerService;
    private StoreService storeService;

    @GetMapping("/list")
    public String customerList(Model model){
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customerList", customerList);
        return "customers/customer_list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") int id){
        customerService.deleteById(id);
        return "redirect:/customers/list";
    }

    @GetMapping()
    public String customerHome(Principal principal, Model model){
        var customer = customerService.findCustomerByAccountName(principal.getName()).get();
        model.addAttribute("customer", customer);
        return "customers/customer_home";
    }
}