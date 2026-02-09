package restaurant.model;

public class Drink extends MenuItem {
    public Drink(int menuItemId, String name, double price, String ingredients, String imageFile, int categoryId) {
        super(menuItemId, name, price, ingredients, imageFile, categoryId);
    }
}