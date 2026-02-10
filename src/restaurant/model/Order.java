package restaurant.model;

public class Order {
    private final int tableNumber;
    private final double totalAmount;

    public Order(int tableNumber, double totalAmount) {
        this.tableNumber = tableNumber;
        this.totalAmount = totalAmount;
    }

    public int getTableNumber() {
        return tableNumber;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
}