package edu.gatech.cs6310.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private int id;
    private String orderNumber;
    private int storeId;
    private List<LineDto> lines = new ArrayList<>();
    private int storeItemId;
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public List<LineDto> getLines() {
        return lines;
    }

    public void setLines(List<LineDto> lines) {
        this.lines = lines;
    }

    public int getStoreItemId() {
        return storeItemId;
    }

    public void setStoreItemId(int storeItemId) {
        this.storeItemId = storeItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
