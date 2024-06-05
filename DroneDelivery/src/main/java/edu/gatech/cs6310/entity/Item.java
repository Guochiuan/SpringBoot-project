package edu.gatech.cs6310.entity;

import lombok.Data;

import javax.persistence.*;

//@Data
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 225, nullable = false)
    private String name;

//    @OneToOne(mappedBy = "item")
//    private Line line;

    @ManyToOne //Many items to One Store
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "weight")
    private int weight;

    @Column(name = "price")
    private double price;

    public Item(String name,
                int weight,
                double price,
                Store store) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.store = store;
    }

    public Item() {}

        public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Line getLine() {
//        return line;
//    }
//
//    public void setLine(Line line) {
//        this.line = line;
//    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}