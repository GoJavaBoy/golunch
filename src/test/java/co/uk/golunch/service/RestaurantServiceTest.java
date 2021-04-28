package co.uk.golunch.service;


import co.uk.golunch.RestaurantTestData;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.User;
import co.uk.golunch.util.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
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
import static co.uk.golunch.model.AbstractBaseEntity.START_SEQ;
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
    // https://stackoverflow.com/questions/27776919/transaction-rollback-after-catching-exception
    @Transactional(rollbackFor = NotFoundException.class)
    void delete() {
        restaurantService.delete(ADMIN_RESTAURANT_HONI_POKE_ID);
        assertThrows(NotFoundException.class, () -> restaurantService.get(ADMIN_RESTAURANT_HONI_POKE_ID));
    }

    @Test
    void getAll() {
        List<Restaurant> all = restaurantService.getAll();
        RESTAURANT_MATCHER.assertMatch(all, userRestaurantFiveGuys, userRestaurantAbsurdBird,
                adminRestaurantHoniPoke, userRestaurantRosaThai, userRestaurantEatActive); // I suppose to sort both collections but I leave it...
    }

    @Test
    @Transactional
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
        int votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 0);
        restaurantService.vote(USER_ID, USER_RESTAURANT_FIVE_GUYS_ID);
        //After vote
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 1);
    }

    @Test
    void deleteUserRecheckVotes() { //Not counting deleted user votes
        //Before vote
        int votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 0);
        restaurantService.vote(USER_ID, USER_RESTAURANT_FIVE_GUYS_ID);
        //After vote
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 1);
        //Delete user and recheck votes
        userService.delete(USER_ID);
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 0);
    }

    @Test
    void updateRecheckVotes() { //Remove all votes if restaurant been updated
        restaurantService.vote(USER_ID, USER_RESTAURANT_FIVE_GUYS_ID);
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

    @Test
    void oneVotePerUser() {
        //Before vote
        int votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 0);
        restaurantService.vote(USER_ID, USER_RESTAURANT_FIVE_GUYS_ID);
        restaurantService.vote(USER_ID, USER_RESTAURANT_FIVE_GUYS_ID);
        //After vote
        votes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(votes, 1);
    }

    @Test
    void changeMindIfBefore11() {
        //Before vote
        int firstRestVotes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(firstRestVotes, 0);
        int secondRestVotes = restaurantService.get(ADMIN_RESTAURANT_HONI_POKE_ID).getVotes();
        assertEquals(secondRestVotes, 1);

        //Vote first time
        restaurantService.vote(USER_ID, USER_RESTAURANT_FIVE_GUYS_ID);
        firstRestVotes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        assertEquals(firstRestVotes, 1);

        //Change mind and vote second time
        restaurantService.vote(USER_ID, ADMIN_RESTAURANT_HONI_POKE_ID);

        //Check
        secondRestVotes = restaurantService.get(ADMIN_RESTAURANT_HONI_POKE_ID).getVotes();
        firstRestVotes = restaurantService.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes();
        LocalDateTime today = new Date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        if (today.getHour() < 11){ // If before 11 remove vote from old restaurant and add to new
            assertEquals(firstRestVotes, 0);
            assertEquals(secondRestVotes, 2);
        } else { // No changes
            assertEquals(firstRestVotes, 1);
            assertEquals(secondRestVotes, 1);
        }
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.delete(NOT_FOUND));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.get(NOT_FOUND));
    }

    @Test
    void voteNotFound() {
        assertThrows(NotFoundException.class, () -> restaurantService.vote(USER_ID, NOT_FOUND));
    }
}