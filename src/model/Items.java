package model;

public class Items {
    private final String category;
    private final String itemName;
    private final double price;
    private int quantity;

    public Items(String category, String itemName, int quantity, double price){
        this.category = category;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Items {" +
                "category: '" + category + '\'' +
                ", itemName: '" + itemName + '\'' +
                ", quantity: " + quantity +
                ", price: " + price +
                '}';
    }
}
