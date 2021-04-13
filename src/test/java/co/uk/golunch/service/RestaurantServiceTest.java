package co.uk.golunch.service;


import co.uk.golunch.RestaurantTestData;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.User;
import co.uk.golunch.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static co.uk.golunch.RestaurantTestData.*;
import static co.uk.golunch.RestaurantTestData.getUpdated;
import static co.uk.golunch.RestaurantTestData.getNew;
import static co.uk.golunch.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.*;


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
        //Before vote
        User user = userService.get(USER_ID);
        int votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 0);
        restaurantService.vote(user.id(), USER_RESTAURANT_FIVE_GUYS_ID);
        //After vote
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 1);
    }

    @Test
    void deleteUserRecheckVotes() { //Not counting deleted user votes
        //Before vote
        User user = userService.get(USER_ID);
        int votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 0);
        restaurantService.vote(user.id(), USER_RESTAURANT_FIVE_GUYS_ID);
        //After vote
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 1);
        //Delete user and recheck votes
        userService.delete(USER_ID);
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 0);
    }

    @Test
    void changeUserMindRecheckVotes() { //Recount votes if user change mind
        //Before vote
        User user = userService.get(USER_ID);
        int votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 0);
        restaurantService.vote(user.id(), USER_RESTAURANT_FIVE_GUYS_ID);
        //After vote
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 1);
        //Change mind and recheck votes.
        restaurantService.vote(user.id(), ADMIN_RESTAURANT_HONI_POKE_ID);
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        if ( LocalDateTime.now().getHour() > 11){
            assertEquals(votes, 1); //If it is after 11:00 changes not happen
        } else {
            assertEquals(votes, 0); //If it is before 11:00 changes happen
        }
    }

    @Test
    void updateRecheckVotes() { //Remove all votes if restaurant been updated
        User user = userService.get(USER_ID);
        restaurantService.vote(user.id(), USER_RESTAURANT_FIVE_GUYS_ID);
        int votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 1);
        Restaurant updated = getUpdated();
        restaurantService.update(updated);
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 0);
    }

    @Test
    void canVote() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy/MM/dd hh:mm");
        LocalDateTime today = new Date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        LocalDateTime yesterday = today.minusDays(1);
        Date lastVoteYesterday = dateFormat.parse(yesterday.getYear()+"/"+yesterday.getMonthValue()+"/"+yesterday.getDayOfMonth() + " 12:00");
        assertTrue(restaurantService.canVote(lastVoteYesterday));
        Date lastVoteToday = dateFormat.parse(today.getYear()+"/"+today.getMonthValue()+"/"+today.getDayOfMonth() + " 10:00");
        if ( LocalDateTime.now().getHour() > 11){
            assertFalse(restaurantService.canVote(lastVoteToday)); //If it is after 11:00 vote can't be changed
        } else {
            assertTrue(restaurantService.canVote(lastVoteToday)); //If it is after 11:00 vote can be changed
        }

    }
}