package com.example.softwareconstructionassign;

/**
 * Author - Abhishek Chetri (u6647717)
 */

public class stock {
    String stock;
    String volume;
    String price;

    public stock(String stock, String volume) {
        this.stock = stock;
        this.volume = volume;
    }

    public stock(String stock, String volume, String price) {
        this.stock = stock;
        this.volume = volume;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
