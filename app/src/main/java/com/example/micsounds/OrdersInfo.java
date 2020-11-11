package com.example.micsounds;


public class OrdersInfo {

    private String date,
                    hours,
                    items,
                    price;

    public OrdersInfo(String date, String hours, String items, String price) {
        this.date = date;
        this.hours = hours;
        this.items = items;
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public String getHours() {
        return hours;
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

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setItems(String items) {
        this.items = items;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
