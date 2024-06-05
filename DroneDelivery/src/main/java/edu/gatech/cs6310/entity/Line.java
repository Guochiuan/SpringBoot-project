package edu.gatech.cs6310.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "line")
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "cost")
    private double cost;

    @Column(name = "weight")
    private int weight;

    public Line(Item item, Orders order, int quantity, double cost, int weight) {
        this.item = item;
        this.order = order;
        this.quantity = quantity;
        this.cost = cost;
        this.weight = weight;
    }

    public Line() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
