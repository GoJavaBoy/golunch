package co.uk.golunch.web.vote;

import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.to.RestaurantTo;
import co.uk.golunch.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static co.uk.golunch.RestaurantTestData.USER_RESTAURANT_FIVE_GUYS_ID;
import static co.uk.golunch.TestUtil.userHttpBasic;
import static co.uk.golunch.UserTestData.user4;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = "/vote/";

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user4)))
                .andExpect(status().isCreated());
        RestaurantTo restaurant = restaurantService.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID);
        assertEquals((int) restaurant.getVotes(), 4);
    }

    @Test
    void voteUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}