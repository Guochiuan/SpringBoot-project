package edu.gatech.cs6310.entity;

import lombok.Data;

import javax.persistence.*;


//@Data
@Entity
@Table(name = "store_item")
public class StoreItem {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne //many store_item point to one store
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    private Store store;

    @ManyToOne //many store_item point to one item
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @Column(name = "price")
    private double price;

    public StoreItem(){}


    public StoreItem(Store store, Item item){
        this.store = store;
        this.item = item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

