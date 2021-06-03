package co.uk.golunch.service;


import co.uk.golunch.RestaurantTestData;
import co.uk.golunch.TestUtil;
import co.uk.golunch.model.MenuItem;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.repository.MenuRepository;
import co.uk.golunch.to.MenuItemTo;
import co.uk.golunch.to.RestaurantTo;
import co.uk.golunch.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static co.uk.golunch.RestaurantTestData.*;
import static co.uk.golunch.TestUtil.toMenuItemTo;
import static org.junit.jupiter.api.Assertions.*;


public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void getWithMenuAndVotes() {
        RestaurantTo restaurant = restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID);
        RESTAURANT_TO_MATCHER.assertMatch(restaurant, RestaurantTestData.userRestaurantToFiveGuys);
    }

    @Test
    // https://stackoverflow.com/questions/27776919/transaction-rollback-after-catching-exception
    @Transactional(rollbackFor = NotFoundException.class)
    void delete() {
        restaurantService.delete(USER_RESTAURANT_FIVE_GUYS_ID);
        assertThrows(NotFoundException.class, () -> restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID));
    }

    @Test
    void getAll() {
        List<Restaurant> all = restaurantService.getAll();
        RESTAURANT_MATCHER.assertMatch(all, userRestaurantFiveGuys, userRestaurantAbsurdBird,
                adminRestaurantHoniPoke, userRestaurantRosaThai, userRestaurantEatActive);
    }

    @Test
    @Transactional
    void update() {
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID);
        assertEquals(updated.getName(), restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID).getName());
    }

    @Test
    void create() {
        Restaurant created = restaurantService.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        assertEquals(created.getName(), restaurantService.getWithMenuAndVotes(newId).getName());
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.delete(NOT_FOUND));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.getWithMenuAndVotes(NOT_FOUND));
    }

    @Test
    void addMenu() {
        Restaurant restaurant = new Restaurant("Restaurant Without Menu");
        Restaurant created = restaurantService.create(restaurant); //Create Restaurant without menu
        assertTrue(menuService.getTodayMenu(created.getId()).isEmpty());
        menuService.addMenu(created.getId(),
                new MenuItemTo(new BigDecimal("10.99"), "Bacon BurgerTS"),
                new MenuItemTo(new BigDecimal("12.99"), "Chicken BurgerTS"),
                new MenuItemTo(new BigDecimal("3.50"), "Chicken NugetsTS"),
                new MenuItemTo(new BigDecimal("1.99"), "Coca-ColaTS"),
                new MenuItemTo(new BigDecimal("1.99"), "SpriteTS")
        );
        List<MenuItemTo> createdMenu = TestUtil.toMenuItemTo(menuService.getTodayMenu(created.getId()));
        MENU_ITEM_TO_MATCHER.assertMatch(createdMenu, menu);
    }

    @Test
    void getTodayMenu() {
        List<MenuItem> menu = menuService.getTodayMenu(USER_RESTAURANT_FIVE_GUYS_ID);
        List<MenuItemTo> menuTo = TestUtil.toMenuItemTo(menu);
        MENU_ITEM_TO_MATCHER.assertMatch(menuTo, userRestaurantToFiveGuys.getMenu());
    }

    @Test
    void updateMenuItem() {
        MenuItemTo updatedMenuItem = new MenuItemTo(new BigDecimal("12.99"), "Updated Chicken Burger", 100012);
        menuService.updateMenuItem(USER_RESTAURANT_FIVE_GUYS_ID, updatedMenuItem);
        MenuItem menuItem = menuRepository.findById(100012).get();
        MENU_ITEM_TO_MATCHER.assertMatch(toMenuItemTo(menuItem), updatedMenuItem);
    }

    @Test
    void deleteMenuItem() {
        menuService.deleteMenuItem(USER_RESTAURANT_FIVE_GUYS_ID, 100012);
        assertThrows(NoSuchElementException.class, () -> menuRepository.findById(100012).get());
    }
}