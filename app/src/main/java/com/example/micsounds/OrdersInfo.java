package com.example.micsounds;


import java.util.HashMap;
import java.util.Map;

public class OrdersInfo {

    private String date;
    private String items;
    private String price;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
    }

    private String shipment;

    public OrdersInfo() {

    }

    public OrdersInfo(String date, String items, String price) {
        this.date = date;
        this.items = items;
        this.price = price;

    }

    public String getDate() {
        return date;
    }

    public String getItems() {
        return items;
    }

    public String getPrice() {
        return price;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", date);
        result.put("price", price);
        result.put("items", items);

        return result;
    }

}
