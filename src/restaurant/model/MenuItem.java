package restaurant.model;

public abstract class MenuItem {
    private final int menuItemId;
    private final String name;
    private final double price;
    private final String ingredients;
    private final String imageFile;
    private final int categoryId;

    public MenuItem(int menuItemId, String name, double price, String ingredients, String imageFile, int categoryId) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.price = price;
        this.ingredients = ingredients;
        this.imageFile = imageFile;
        this.categoryId = categoryId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getImageFile() {
        return imageFile;
    }
}