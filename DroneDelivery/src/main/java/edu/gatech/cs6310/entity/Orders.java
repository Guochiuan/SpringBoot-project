package edu.gatech.cs6310.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number", length = 225, nullable = false, unique = false)
    private String orderNumber;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone; // foreign key

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer; // foreign key

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store; // foreign key

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.LAZY)
    private List<Line> orderLines = new ArrayList<>();

    @Column(name = "total_cost")
    private int totalCost;

    @Column(name = "total_weight")
    private int totalWeight;

    public Orders(String orderNumber, Drone drone, Customer customer, Store store, List<Line> orderLines, int totalCost, int totalWeight) {
        this.orderNumber = orderNumber;
        this.drone = drone;
        this.customer = customer;
        this.store = store;
        this.orderLines = orderLines;
        this.totalCost = totalCost;
        this.totalWeight = totalWeight;
    }

    public Orders() {}

    public String getOrderNumber() {
        return orderNumber;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public List<Line> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<Line> orderLines) {
        this.orderLines = orderLines;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(int totalWeight) {
        this.totalWeight = totalWeight;
    }
}
