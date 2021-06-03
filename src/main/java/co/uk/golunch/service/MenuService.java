package co.uk.golunch.service;

import co.uk.golunch.model.MenuItem;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.repository.MenuRepository;
import co.uk.golunch.repository.RestaurantRepository;
import co.uk.golunch.to.MenuItemTo;
import co.uk.golunch.util.exception.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static co.uk.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MenuService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public MenuService(RestaurantRepository restaurantRepository, MenuRepository menuRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }


    @Transactional
    public void addMenu(int id, MenuItemTo... menuItems) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
        Assert.notNull(menuItems, "menu must not be null");
        if (menuItems.length == 0) {
            throw new IllegalArgumentException("menu must not be empty");
        }
        List<MenuItem> menu = Arrays.stream(menuItems)
                .map(menuItemTo -> new MenuItem(menuItemTo, restaurant))
                .collect(Collectors.toList());
        menuRepository.saveAll(menu);
    }

    public List<MenuItem> getTodayMenu(int id) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
        return menuRepository.findAllByRestaurantAndDate(restaurant, LocalDate.now());
    }

    @Transactional
    public void updateMenuItem(int restaurantId, MenuItemTo menuItemTo) {
        Assert.notNull(menuItemTo, "menuItem must not be null");
        MenuItem menuItem = getMenuItem(restaurantId, menuItemTo.getId());
        if (!menuItemTo.isNew() && menuItem == null) {
            throw new DataIntegrityViolationException("menuItem must not be new or null");
        }
        menuItem.setDate(LocalDate.now());
        menuItem.setName(menuItemTo.getName());
        menuItem.setPrice(menuItemTo.getPrice());
    }

    @Transactional
    public void deleteMenuItem(int restaurantId, int menuItemId) {
        MenuItem menuItem = getMenuItem(restaurantId, menuItemId);
        if (menuItem == null) {
            throw new NotFoundException("wrong restaurant or menuItem id");
        }
        menuRepository.deleteById(menuItemId);
    }

    private MenuItem getMenuItem(int restaurantId, Integer menuItemId) {
        if (menuItemId == null) {
            throw new DataIntegrityViolationException("provide id when updating menuItem");
        }
        return menuRepository.findByIdAndRestaurantId(menuItemId, restaurantId);
    }
}
