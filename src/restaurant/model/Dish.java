package restaurant.model;

public class Dish extends MenuItem {
    public Dish(int menuItemId, String name, double price, String ingredients, String imageFile, int categoryId) {
        super(menuItemId, name, price, ingredients, imageFile, categoryId);
    }
}