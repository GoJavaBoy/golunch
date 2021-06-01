package co.uk.golunch.web.restaurant;

import co.uk.golunch.RestaurantTestData;
import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.repository.MenuRepository;
import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.to.DishTo;
import co.uk.golunch.util.exception.NotFoundException;
import co.uk.golunch.web.AbstractControllerTest;
import co.uk.golunch.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static co.uk.golunch.RestaurantTestData.*;
import static co.uk.golunch.TestUtil.*;
import static co.uk.golunch.UserTestData.admin;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminRestaurantRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantRestController.REST_URL + '/';

    @Autowired
    private RestaurantService service;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID).contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertEquals(service.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID).getName(), updated.getName());
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
        assertEquals(service.getWithMenuAndVotes(newId).getName(), newRestaurant.getName());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(userRestaurantToFiveGuys));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
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
        assertThrows(NotFoundException.class, () -> service.getWithMenuAndVotes(USER_RESTAURANT_FIVE_GUYS_ID));
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant("");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(adminRestaurantHoniPoke);
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Restaurant invalid = new Restaurant("Five Guys");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Restaurant invalid = new Restaurant(adminRestaurantHoniPoke);
        invalid.setName("Five Guys");
        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void addMenu() throws Exception {
        Restaurant restaurant = new Restaurant("Restaurant Without Menu");
        Restaurant created = service.create(restaurant); //Create Restaurant without menu
        assertTrue(service.getTodayMenu(created.getId()).isEmpty());
        perform(MockMvcRequestBuilders.post(REST_URL + created.getId() + "/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(menu)))
                .andExpect(status().isCreated());
        List<DishTo> createdMenu = toDishTo(service.getTodayMenu(created.getId()));
        DISH_TO_MATCHER.assertMatch(createdMenu, menu);
    }

    @Test
    void getTodayMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID + "/menu")
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(toDish(userRestaurantToFiveGuys.getMenu())));
    }

    @Test
    void updateDish() throws Exception {
        DishTo updatedDish = new DishTo(new BigDecimal("12.99"), "Updated Chicken Burger", 100012);
        perform(MockMvcRequestBuilders.put(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID+"/menu")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(admin))
                .content(JsonUtil.writeValue(updatedDish)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Dish dish = menuRepository.findById(100012).get();
        DISH_TO_MATCHER.assertMatch(toDishTo(dish), updatedDish);
    }

    @Test
    void deleteDish() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID+"/menu/"+100012)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NoSuchElementException.class, () -> menuRepository.findById(100012).get());
    }
}