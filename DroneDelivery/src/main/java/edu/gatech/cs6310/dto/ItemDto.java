package edu.gatech.cs6310.dto;

public class ItemDto {
    private String name;
    private int weight;
    private double price;
    private int storeId;
    private int itemId;

    public ItemDto(String name, int weight, double price, int storeId, int itemId) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.storeId = storeId;
        this.itemId = itemId;
    }

    public ItemDto() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
