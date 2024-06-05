package edu.gatech.cs6310.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "drone")
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "serial_num")
    private String serialNum;

    @ManyToOne //many drones point to ONE store, one drone can't point to more than ONE store at the same time
    @JoinColumn(name = "store_id") //done
    private Store store; // foreign key

    @OneToOne(mappedBy = "drone")
    private Pilot pilot;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "drone", fetch = FetchType.LAZY) //one drone can have 1+ orders, but 1 order can only have one drone
    private List<Orders> droneOrders = new ArrayList<>();

    @Column(name ="lift_cap")
    private int liftCap;

    @Column(name ="num_deliveries_left")
    private int numDeliveriesLeft;

    @Column(name ="num_orders")
    private int numOrders;

    @Column(name ="remaining_cap")
    private int remainingCap;

    @Column(name ="available_cap")
    private int availableCap;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    public List<Orders> getDroneOrders() {
        return droneOrders;
    }

    public void setDroneOrders(List<Orders> droneOrders) {
        this.droneOrders = droneOrders;
    }

    public int getLiftCap() {
        return liftCap;
    }

    public void setLiftCap(int liftCap) {
        this.liftCap = liftCap;
    }

    public int getNumDeliveriesLeft() {
        return numDeliveriesLeft;
    }

    public void setNumDeliveriesLeft(int numDeliveriesLeft) {
        this.numDeliveriesLeft = numDeliveriesLeft;
    }

    public int getNumOrders() {
        return numOrders;
    }

    public void setNumOrders(int numOrders) {
        this.numOrders = numOrders;
    }

    public int getRemainingCap() {
        return remainingCap;
    }

    public void setRemainingCap(int remainingCap) {
        this.remainingCap = remainingCap;
    }

    public int getAvailableCap() {
        return availableCap;
    }

    public void setAvailableCap(int availableCap) {
        this.availableCap = availableCap;
    }

    public void addOrder(Orders order) {
        availableCap -= order.getTotalWeight();
        numOrders++;
        droneOrders.add(order);
    }
}
