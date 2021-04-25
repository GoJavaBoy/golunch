package co.uk.golunch.web.restaurant;

import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static co.uk.golunch.RestaurantTestData.*;
import static co.uk.golunch.TestUtil.userHttpBasic;
import static co.uk.golunch.UserTestData.admin;
import static co.uk.golunch.UserTestData.user;
import static co.uk.golunch.model.AbstractBaseEntity.START_SEQ;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserRestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantService service;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(userRestaurantFiveGuys, userRestaurantAbsurdBird, adminRestaurantHoniPoke,
                        userRestaurantRosaThai, userRestaurantEatActive
                ));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(userRestaurantFiveGuys));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID + "/vote")
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertEquals((adminRestaurantHoniPoke.getVotes() + 1), service.get(ADMIN_RESTAURANT_HONI_POKE_ID).getVotes());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void voteUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID + "/vote"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void oneVotePerUser() throws Exception {
        //Same user try to vote for same restaurant. Nothing happen.
        perform(MockMvcRequestBuilders.patch(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID + "/vote")
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.patch(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID + "/vote")
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertEquals((adminRestaurantHoniPoke.getVotes() + 1), service.get(ADMIN_RESTAURANT_HONI_POKE_ID).getVotes());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void changeMindIfBefore11() throws Exception {
        //Vote first time
        perform(MockMvcRequestBuilders.patch(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID + "/vote")
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertEquals((adminRestaurantHoniPoke.getVotes() + 1), service.get(ADMIN_RESTAURANT_HONI_POKE_ID).getVotes());

        //Change mind and vote second time
        perform(MockMvcRequestBuilders.patch(REST_URL + userRestaurantRosaThai.getId() + "/vote")
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());

        //Check
        LocalDateTime today = new Date().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        if (today.getHour() < 11){ // If before 11 remove vote from old restaurant and add to new
            Assertions.assertEquals((userRestaurantRosaThai.getVotes() + 1), service.get(START_SEQ + 3).getVotes());
            Assertions.assertEquals((adminRestaurantHoniPoke.getVotes()), service.get(ADMIN_RESTAURANT_HONI_POKE_ID).getVotes());
        } else { // No changes
            Assertions.assertEquals(userRestaurantRosaThai.getVotes(), service.get(START_SEQ + 3).getVotes());
            Assertions.assertEquals((adminRestaurantHoniPoke.getVotes() + 1), service.get(ADMIN_RESTAURANT_HONI_POKE_ID).getVotes());
        }
    }
}