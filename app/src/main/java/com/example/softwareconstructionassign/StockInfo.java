package com.example.softwareconstructionassign;

public class StockInfo {
    String stock;
    String volume;

    public StockInfo(String stock, String volume) {
        this.stock = stock;
        this.volume = volume;
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
