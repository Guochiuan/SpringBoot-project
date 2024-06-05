package edu.gatech.cs6310.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 225, nullable = false, unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager; // foreign key

    @ToString.Exclude
    @OneToMany //one store can have 1+ orders, but 1 order can only have one store
    @JoinColumn(name = "store_id") //done
    private List<Orders> storeOrders = new ArrayList<>();

    @ToString.Exclude
    @OneToMany //one store can have 1+ drones, but 1 drone can only have one store
    @JoinColumn(name = "store_id") //done
    private List<Drone> storeDrones = new ArrayList<>();

    @Column(name = "incoming_revenue")
    private int incomingRevenue;

    @Column(name = "completed_revenue")
    private int completedRevenue;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "store")
    private List<StoreItem> storeItems = new ArrayList<>();
    public Store(String name, int completedRevenue, int incomingRevenue) {
        this.name = name;
        this.completedRevenue = completedRevenue;
        this.incomingRevenue = incomingRevenue;
    }

    public Store() {}

    public Store(String name, Manager manager, List<Orders> storeOrders, List<Drone> storeDrones, int incomingRevenue, int completedRevenue, List<StoreItem> storeItems) {
        this.name = name;
        this.manager = manager;
        this.storeOrders = storeOrders;
        this.storeDrones = storeDrones;
        this.incomingRevenue = incomingRevenue;
        this.completedRevenue = completedRevenue;
        this.storeItems = storeItems;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public List<Orders> getStoreOrders() {
        return storeOrders;
    }

    public void setStoreOrders(List<Orders> storeOrders) {
        this.storeOrders = storeOrders;
    }

    public List<Drone> getStoreDrones() {
        return storeDrones;
    }

    public void setStoreDrones(List<Drone> storeDrones) {
        this.storeDrones = storeDrones;
    }

    public int getIncomingRevenue() {
        return incomingRevenue;
    }

    public void setIncomingRevenue(int incomingRevenue) {
        this.incomingRevenue = incomingRevenue;
    }

    public int getCompletedRevenue() {
        return completedRevenue;
    }

    public void setCompletedRevenue(int completedRevenue) {
        this.completedRevenue = completedRevenue;
    }

    public List<StoreItem> getStoreItems() {
        return storeItems;
    }

    public void setStoreItems(List<StoreItem> storeItems) {
        this.storeItems = storeItems;
    }
}