package co.uk.golunch;

import co.uk.golunch.model.MenuItem;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.to.MenuItemTo;
import co.uk.golunch.to.RestaurantTo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static co.uk.golunch.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class);
    public static final TestMatcher<RestaurantTo> RESTAURANT_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(RestaurantTo.class, "votes");
    public static final TestMatcher<MenuItemTo> MENU_ITEM_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(MenuItemTo.class, "id");
    public static final TestMatcher<MenuItem> MENU_ITEM_MATCHER = TestMatcher.usingIgnoringFieldsComparator(MenuItem.class, "date", "restaurant");

    public static final int NOT_FOUND = 10;
    public static final int USER_RESTAURANT_FIVE_GUYS_ID = START_SEQ;
    public static final int ADMIN_RESTAURANT_HONI_POKE_ID = START_SEQ + 2;

    public static final Restaurant userRestaurantFiveGuys = new Restaurant(USER_RESTAURANT_FIVE_GUYS_ID, "Five Guys");
    public static final RestaurantTo userRestaurantToFiveGuys = new RestaurantTo();
    public static final Restaurant userRestaurantAbsurdBird = new Restaurant(START_SEQ + 1, "Absurd Bird");
    public static final Restaurant userRestaurantRosaThai = new Restaurant(START_SEQ + 3, "Rosa Thai");
    public static final Restaurant userRestaurantEatActive = new Restaurant(START_SEQ + 4, "Eat Active");
    public static final Restaurant adminRestaurantHoniPoke = new Restaurant(ADMIN_RESTAURANT_HONI_POKE_ID, "Honi Poke");

    public static final List<MenuItemTo> menu = Arrays.asList(
            new MenuItemTo(new BigDecimal("10.99"), "Bacon BurgerTS"),
            new MenuItemTo(new BigDecimal("12.99"), "Chicken BurgerTS"),
            new MenuItemTo(new BigDecimal("3.50"), "Chicken NugetsTS"),
            new MenuItemTo(new BigDecimal("1.99"), "Coca-ColaTS"),
            new MenuItemTo(new BigDecimal("1.99"), "SpriteTS")
    );

    static {
        List<MenuItemTo> menu = Arrays.asList(
                new MenuItemTo(new BigDecimal("10.99"), "Bacon Burger", 100013),
                new MenuItemTo(new BigDecimal("12.99"), "Chicken Burger", 100012),
                new MenuItemTo(new BigDecimal("3.50"), "Chicken Nugets", 100014),
                new MenuItemTo(new BigDecimal("1.99"), "Coca-Cola", 100015),
                new MenuItemTo(new BigDecimal("1.99"), "Sprite", 100016)
        );
        userRestaurantToFiveGuys.setName(userRestaurantFiveGuys.getName());
        userRestaurantToFiveGuys.setVotes(3);
        userRestaurantToFiveGuys.setMenu(menu);
    }

    public static Restaurant getNew() {
        Restaurant newRestaurant = new Restaurant(null, "New Restaurant");
        return newRestaurant;
    }

    public static Restaurant getUpdated() {
        Restaurant updatedRestaurant = new Restaurant(USER_RESTAURANT_FIVE_GUYS_ID, userRestaurantFiveGuys.getName() + " Updated");
        return updatedRestaurant;
    }
}
