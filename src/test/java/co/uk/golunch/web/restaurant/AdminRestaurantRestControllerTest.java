//package co.uk.golunch.web.restaurant;
//
//import co.uk.golunch.RestaurantTestData;
//import co.uk.golunch.model.Dish;
//import co.uk.golunch.model.Restaurant;
//import co.uk.golunch.service.RestaurantService;
//import co.uk.golunch.util.exception.NotFoundException;
//import co.uk.golunch.web.AbstractControllerTest;
//import co.uk.golunch.web.json.JsonUtil;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import java.math.BigDecimal;
//import java.util.Set;
//
//import static co.uk.golunch.RestaurantTestData.NOT_FOUND;
//import static co.uk.golunch.TestUtil.readFromJson;
//import static co.uk.golunch.TestUtil.userHttpBasic;
//import static co.uk.golunch.RestaurantTestData.*;
//import static co.uk.golunch.UserTestData.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class AdminRestaurantRestControllerTest extends AbstractControllerTest {
//
//    private static final String REST_URL = AdminRestaurantRestController.REST_URL + '/';
//
//    @Autowired
//    private RestaurantService service;
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Test
//    void update() throws Exception {
//        Restaurant updated = RestaurantTestData.getUpdated();
//        perform(MockMvcRequestBuilders.put(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID).contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(admin))
//                .content(JsonUtil.writeValue(updated)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//        RESTAURANT_MATCHER.assertMatch(service.get(USER_RESTAURANT_FIVE_GUYS_ID), updated);
//    }
//
//    @Test
//    void createWithLocation() throws Exception {
//        Restaurant newRestaurant = RestaurantTestData.getNew();
//        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(admin))
//                .content(JsonUtil.writeValue(newRestaurant)))
//                .andExpect(status().isCreated());
//
//        Restaurant created = readFromJson(action, Restaurant.class);
//        int newId = created.id();
//        newRestaurant.setId(newId);
//        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
//        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
//    }
//
//    @Test
//    void get() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID)
//                .with(userHttpBasic(admin)))
//                .andExpect(status().isOk())
//                .andDo(print())
//                // https://jira.spring.io/browse/SPR-14472
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(RESTAURANT_MATCHER.contentJson(userRestaurantFiveGuys));
//    }
//
//    @Test
//    void getNotFound() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL + NOT_FOUND)
//                .with(userHttpBasic(admin)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//
//    @Test
//    void getAll() throws Exception {
//        perform(MockMvcRequestBuilders.get(REST_URL)
//                .with(userHttpBasic(admin)))
//                .andExpect(status().isOk())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect(RESTAURANT_MATCHER.contentJson(userRestaurantFiveGuys, userRestaurantAbsurdBird, adminRestaurantHoniPoke,
//                        userRestaurantRosaThai, userRestaurantEatActive
//                ));
//    }
//
//    @Test
//    void delete() throws Exception {
//        perform(MockMvcRequestBuilders.delete(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID)
//                .with(userHttpBasic(admin)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//        entityManager.clear();
//        assertThrows(NotFoundException.class, () -> service.get(USER_RESTAURANT_FIVE_GUYS_ID));
//    }
//
//    @Test
//    void vote() throws Exception {
//        perform(MockMvcRequestBuilders.patch(REST_URL + USER_RESTAURANT_FIVE_GUYS_ID + "/vote")
//                .with(userHttpBasic(admin)))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//        entityManager.flush();
//        entityManager.clear();
//        Assertions.assertEquals((userRestaurantFiveGuys.getVotes() + 1), service.get(USER_RESTAURANT_FIVE_GUYS_ID).getVotes());
//    }
//
//    @Test
//    void createInvalid() throws Exception {
//        Restaurant invalid = new Restaurant("");
//        perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(admin))
//                .content(JsonUtil.writeValue(invalid)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    void updateInvalid() throws Exception {
//        Restaurant invalid = new Restaurant(adminRestaurantHoniPoke);
//        invalid.setName("");
//        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(admin))
//                .content(JsonUtil.writeValue(invalid)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void createDuplicate() throws Exception {
//        Restaurant invalid = new Restaurant("Five Guys");
//        perform(MockMvcRequestBuilders.post(REST_URL)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(admin))
//                .content(JsonUtil.writeValue(invalid)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void updateDuplicate() throws Exception {
//        Restaurant invalid = new Restaurant(adminRestaurantHoniPoke);
//        invalid.setName("Five Guys");
//        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(admin))
//                .content(JsonUtil.writeValue(invalid)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//    @Test
//    @Transactional(propagation = Propagation.NEVER)
//    void createInvalidMenu() throws Exception {
//        Restaurant invalid = new Restaurant(adminRestaurantHoniPoke);
//        invalid.setMenu(Set.of(
//                new Dish("Salmon Poke", null),
//                new Dish("Avocado Poke", new BigDecimal("5.99")),
//                new Dish("Ginger Shot", new BigDecimal("5.99"))
//        ));
//        perform(MockMvcRequestBuilders.put(REST_URL + ADMIN_RESTAURANT_HONI_POKE_ID)
//                .contentType(MediaType.APPLICATION_JSON)
//                .with(userHttpBasic(admin))
//                .content(JsonUtil.writeValue(invalid)))
//                .andDo(print())
//                .andExpect(status().isUnprocessableEntity());
//    }
//
//}