package co.uk.golunch.service;


import co.uk.golunch.RestaurantTestData;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static co.uk.golunch.RestaurantTestData.*;
import static co.uk.golunch.RestaurantTestData.getUpdated;
import static co.uk.golunch.RestaurantTestData.getNew;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService userService;

    @Autowired
    RestaurantService restaurantService;

    @Test
    void get() {
        Restaurant restaurant = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, RestaurantTestData.userRestaurantFiveGuys);
    }

    @Test
    void delete() {
        restaurantService.delete(USER_RESTAURANT_FIVE_GUYS_ID);
        assertThrows(NotFoundException.class, () -> restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID));
    }

    @Test
    void getAll() {
        List<Restaurant> all = restaurantService.getAll();
        RESTAURANT_MATCHER.assertMatch(all, userRestaurantFiveGuys, userRestaurantAbsurdBird,
                adminRestaurantHoniPoke, userRestaurantRosaThai, userRestaurantEatActive); // I suppose to sort both collections but I leave it...
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID), getUpdated());
    }

    @Test
    void create() {
        Restaurant created = restaurantService.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    void vote() {

    }
}