package co.uk.golunch.service;

import co.uk.golunch.to.RestaurantTo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

import static co.uk.golunch.RestaurantTestData.ADMIN_RESTAURANT_HONI_POKE_ID;
import static co.uk.golunch.RestaurantTestData.USER_RESTAURANT_FIVE_GUYS_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VotesServiceTest extends AbstractServiceTest {

    @Autowired
    private VotesService votesService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    void save() {
        RestaurantTo restaurant = restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID);
        assertEquals((int) restaurant.getVotes(), 3);
        votesService.save(userService.get(100010), USER_RESTAURANT_FIVE_GUYS_ID);
        restaurant = restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID);
        assertEquals((int) restaurant.getVotes(), 4);
    }

    @Test
    @Transactional
    void oneVotePerUser() {
        RestaurantTo restaurant = restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID);
        assertEquals((int) restaurant.getVotes(), 3);
        votesService.save(userService.get(100010), USER_RESTAURANT_FIVE_GUYS_ID);
        votesService.save(userService.get(100010), USER_RESTAURANT_FIVE_GUYS_ID);
        restaurant = restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID);
        assertEquals((int) restaurant.getVotes(), 4);
    }

    @Test
    @Transactional
    void changeMind() {
        RestaurantTo restaurant = restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID);
        assertEquals((int) restaurant.getVotes(), 3);
        votesService.save(userService.get(100010), ADMIN_RESTAURANT_HONI_POKE_ID);
        votesService.save(userService.get(100010), USER_RESTAURANT_FIVE_GUYS_ID);
        restaurant = restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID);
        if (LocalTime.now().isBefore(LocalTime.parse("11:00"))) {
            assertEquals((int) restaurant.getVotes(), 4);
        } else assertEquals((int) restaurant.getVotes(), 3);
    }
}