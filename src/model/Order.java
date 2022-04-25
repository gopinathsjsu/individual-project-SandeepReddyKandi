package model;

import java.time.LocalDateTime;
import java.util.HashSet;

public class Order {
    private LocalDateTime orderDate = LocalDateTime.now();
    private double totalPrice;
    private HashSet<Items> items = new HashSet<>();

    public Order() {}

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public HashSet<Items> getItems() {
        return items;
    }

    public void setItems(HashSet<Items> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order {" +
                "orderDate: " + orderDate +
                ", totalPrice: " + totalPrice +
                ", items: " + items +
                '}';
    }
}
