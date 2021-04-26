package co.uk.golunch.web.restaurant;

import co.uk.golunch.RestaurantTestData;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.util.exception.NotFoundException;
import co.uk.golunch.web.AbstractControllerTest;
import co.uk.golunch.web.json.JsonUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static co.uk.golunch.TestUtil.readFromJson;
import static co.uk.golunch.TestUtil.userHttpBasic;
import static co.uk.golunch.RestaurantTestData.*;
import static co.uk.golunch.UserTestData.admin;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantService service;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(service.get(USER_RESTAURANT_FIVE_GUYS_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = readFromJson(action, Restaurant.class);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(userRestaurantFiveGuys));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(userRestaurantFiveGuys, userRestaurantAbsurdBird, adminRestaurantHoniPoke,
                        userRestaurantRosaThai, userRestaurantEatActive
                ));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        entityManager.clear();
        assertThrows(NotFoundException.class, () -> service.get(USER_RESTAURANT_FIVE_GUYS_ID));
    }

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.patch(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID + "/vote")
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        entityManager.flush();
        entityManager.clear();
        Assertions.assertEquals((userRestaurantFiveGuys.getVotes() + 1), service.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes());
    }
}