//package co.uk.golunch.web.json;
//
//import co.uk.golunch.model.Restaurant;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//
//import static co.uk.golunch.RestaurantTestData.*;
//
//public class JsonUtilTest {
//    @Test
//    void readWriteValue() {
//        String json = JsonUtil.writeValue(userRestaurantRosaThai);
//        System.out.println(json);
//        Restaurant restaurant = JsonUtil.readValue(json, Restaurant.class);
//        RESTAURANT_MATCHER.assertMatch(restaurant, userRestaurantRosaThai);
//    }
//
//    @Test
//    void readWriteValues() {
//        String json = JsonUtil.writeValue(List.of(
//                userRestaurantRosaThai,
//                userRestaurantAbsurdBird,
//                userRestaurantEatActive
//                ));
//        System.out.println(json);
//        List<Restaurant> restaurants = JsonUtil.readValues(json, Restaurant.class);
//        RESTAURANT_MATCHER.assertMatch(restaurants, List.of(
//                userRestaurantRosaThai,
//                userRestaurantAbsurdBird,
//                userRestaurantEatActive
//        ));
//    }
//}
