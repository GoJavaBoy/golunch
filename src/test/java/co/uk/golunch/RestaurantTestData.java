package co.uk.golunch;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;

import java.math.BigDecimal;
import java.util.Set;

import static co.uk.golunch.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int NOT_FOUND = 10;
    public static final int USER_RESTAURANT_FIVE_GUYS_ID = START_SEQ;
    public static final int ADMIN_RESTAURANT_HONI_POKE_ID = START_SEQ + 2;

    public static final Restaurant userRestaurantFiveGuys = new Restaurant(USER_RESTAURANT_FIVE_GUYS_ID, "Five Guys");
    public static final Restaurant userRestaurantAbsurdBird = new Restaurant(START_SEQ + 1, "Absurd Bird");
    public static final Restaurant userRestaurantRosaThai = new Restaurant(START_SEQ + 3, "Rosa Thai");
    public static final Restaurant userRestaurantEatActive = new Restaurant(START_SEQ + 4, "Eat Active");
    public static final Restaurant adminRestaurantHoniPoke = new Restaurant(ADMIN_RESTAURANT_HONI_POKE_ID, "Honi Poke");

    static {
        userRestaurantFiveGuys.setMenu(Set.of(
                new Dish("Chicken Burger", new BigDecimal("12.99")),
                new Dish("Bacon Burger", new BigDecimal("10.99")),
                new Dish("Chicken Nugets", new BigDecimal("3.50")),
                new Dish("Coca-Cola", new BigDecimal("1.99")),
                new Dish("Sprite", new BigDecimal("1.99"))
        ));
        userRestaurantAbsurdBird.setMenu(Set.of(
                new Dish("Big Roaster", new BigDecimal("11.99")),
                new Dish("Chips", new BigDecimal("3.85")),
                new Dish("Chocolate Cake", new BigDecimal("5.10")),
                new Dish("Fanta", new BigDecimal("1.99"))
        ));
        userRestaurantAbsurdBird.setVotes(2);
        adminRestaurantHoniPoke.setMenu(Set.of(
                new Dish("Salmon Poke", new BigDecimal("5.99")),
                new Dish("Avocado Poke", new BigDecimal("5.99")),
                new Dish("Ginger Shot", new BigDecimal("5.99"))
        ));
        adminRestaurantHoniPoke.setVotes(1);
        userRestaurantRosaThai.setMenu(Set.of(
                new Dish("Thai Pad", new BigDecimal("15.85")),
                new Dish("Thai Beef Pasta", new BigDecimal("8.75")),
                new Dish("Thai Menu of The Day", new BigDecimal("25.10")),
                new Dish("Thai Drink", new BigDecimal("2.99"))
        ));
        userRestaurantRosaThai.setVotes(3);
        userRestaurantEatActive.setMenu(Set.of(
                new Dish("Deal for One", new BigDecimal("15.20")),
                new Dish("Potatoes with Avocado", new BigDecimal("8.90")),
                new Dish("Beef on Grill", new BigDecimal("7.25")),
                new Dish("Lemon Juice", new BigDecimal("3.25"))
        ));
        userRestaurantEatActive.setVotes(1);
    }

    public static Restaurant getNew() {
        Restaurant newRestaurant = new Restaurant(null, "New Restaurant");
        newRestaurant.setMenu(Set.of(
                new Dish("New dish #1", new BigDecimal("5.35")),
                new Dish("New dish #2", new BigDecimal("0.90")),
                new Dish("New dish #3", new BigDecimal("1.99"))
        ));
        return newRestaurant;
    }

    public static Restaurant getUpdated() {
        Restaurant updatedRestaurant = new Restaurant(USER_RESTAURANT_FIVE_GUYS_ID, userRestaurantFiveGuys.getName() + " Updated");
        updatedRestaurant.setMenu(Set.of(
                new Dish("Chicken Burger", new BigDecimal("12.99")),
                new Dish("Bacon Burger", new BigDecimal("10.99")),
                new Dish("Chicken Nugets", new BigDecimal("3.50")),
                new Dish("Coca-Cola", new BigDecimal("1.99")),
                new Dish("Sprite", new BigDecimal("1.99")),
                new Dish("Updater Restaurant Dish #1", new BigDecimal("15.25")),
                new Dish("Updater Restaurant Dish #2", new BigDecimal("20.99"))
        ));
        return updatedRestaurant;
    }
}
