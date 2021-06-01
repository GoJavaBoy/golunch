package co.uk.golunch.service;


import co.uk.golunch.RestaurantTestData;
import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.repository.MenuRepository;
import co.uk.golunch.to.DishTo;
import co.uk.golunch.to.RestaurantTo;
import co.uk.golunch.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static co.uk.golunch.RestaurantTestData.*;
import static co.uk.golunch.TestUtil.toDishTo;
import static org.junit.jupiter.api.Assertions.*;


public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService restaurantService;

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
        assertTrue(restaurantService.getTodayMenu(created.getId()).isEmpty());
        restaurantService.addMenu(created.getId(),
                new DishTo(new BigDecimal("10.99"), "Bacon BurgerTS"),
                new DishTo(new BigDecimal("12.99"), "Chicken BurgerTS"),
                new DishTo(new BigDecimal("3.50"), "Chicken NugetsTS"),
                new DishTo(new BigDecimal("1.99"), "Coca-ColaTS"),
                new DishTo(new BigDecimal("1.99"), "SpriteTS")
        );
        List<DishTo> createdMenu = toDishTo(restaurantService.getTodayMenu(created.getId()));
        DISH_TO_MATCHER.assertMatch(createdMenu, menu);
    }

    @Test
    void getTodayMenu() {
        List<Dish> menu = restaurantService.getTodayMenu(USER_RESTAURANT_FIVE_GUYS_ID);
        List<DishTo> menuTo = toDishTo(menu);
        DISH_TO_MATCHER.assertMatch(menuTo, userRestaurantToFiveGuys.getMenu());
    }

    @Test
    void updateDish() {
        DishTo updatedDish = new DishTo(new BigDecimal("12.99"), "Updated Chicken Burger", 100012);
        restaurantService.updateDish(USER_RESTAURANT_FIVE_GUYS_ID, updatedDish);
        Dish dish = menuRepository.findById(100012).get();
        DISH_TO_MATCHER.assertMatch(toDishTo(dish), updatedDish);
    }

    @Test
    void deleteDish() {
        restaurantService.deleteDish(USER_RESTAURANT_FIVE_GUYS_ID, 100012);
        assertThrows(NoSuchElementException.class, () -> menuRepository.findById(100012).get());
    }
}