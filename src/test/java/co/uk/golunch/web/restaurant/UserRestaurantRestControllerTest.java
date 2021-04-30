package co.uk.golunch.web.restaurant;

import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.web.AbstractControllerTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static co.uk.golunch.RestaurantTestData.*;
import static co.uk.golunch.TestUtil.userHttpBasic;
import static co.uk.golunch.UserTestData.admin;
import static co.uk.golunch.UserTestData.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = UserRestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantService service;

    @PersistenceContext
    private EntityManager entityManager;

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
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID + "/vote")
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
        entityManager.flush();
        entityManager.clear();
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
}