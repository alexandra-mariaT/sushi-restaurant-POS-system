package restaurant.controller;

import restaurant.model.MenuItem;
import restaurant.repository.MenuItemsRepository;

import java.util.List;

public class MenuController {
    private final MenuItemsRepository menuItemRepository;

    public MenuController() {
        this.menuItemRepository = new MenuItemsRepository();
    }

    public List<MenuItem> getFullMenu() {
        return menuItemRepository.getAllMenuItems();
    }
}