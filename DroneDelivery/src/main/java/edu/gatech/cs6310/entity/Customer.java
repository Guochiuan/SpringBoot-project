package edu.gatech.cs6310.entity;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Data
@Entity
@Table(name = "customer")
public class Customer extends User {
    @OneToMany(cascade = CascadeType.ALL) //one customer can have many orders, one order can have one customer
    @JoinColumn(name = "customer_id")//done
    private List<Orders> customerOrders = new ArrayList<>();

    @Column(name = "credits")
    private int credits;

    @Column(name = "rating")
    private int rating;

    @Column(name = "available_credit")
    private int availableCredit;

    public List<Orders> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<Orders> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(int availableCredit) {
        this.availableCredit = availableCredit;
    }
}
