package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.MenuItem;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.to.MenuItemTo;
import co.uk.golunch.to.RestaurantTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static co.uk.golunch.util.ValidationUtil.assureIdConsistent;
import static co.uk.golunch.util.ValidationUtil.checkNew;

public class AbstractRestaurantController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService restaurantService;

    public void update(int id, Restaurant restaurant) {
        log.info("update {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantService.update(restaurant);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return restaurantService.create(restaurant);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantService.getAll();
    }

    public List<RestaurantTo> getAllWithMenu() {
        log.info("getAll with menu");
        return restaurantService.getAllWithMenu();
    }

    public void delete(int id) {
        log.info("delete {}", id);
        restaurantService.delete(id);
    }

    public RestaurantTo getWithMenuAndVotes(int id) {
        log.info("get restaurant with menu and votes {}", id);
        return restaurantService.getWithMenuAndVotes(id);
    }

    public RestaurantTo getWithMenu(int id) {
        log.info("get restaurant with menu {}", id);
        return restaurantService.getWithMenu(id);
    }
}
