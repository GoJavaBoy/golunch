package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.to.DishTo;
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

    public void delete(int id) {
        log.info("delete {}", id);
        restaurantService.delete(id);
    }

    public RestaurantTo getWithMenuAndVotes(int id) {
        log.info("get restaurant with menu and votes {}", id);
        return restaurantService.getWithMenuAndVotes(id);
    }

    public void addMenu(int id, DishTo... menu) {
        log.info("add menu for restaurant {}", id);
        restaurantService.addMenu(id, menu);
    }

    public List<Dish> getTodayMenu(int id) {
        log.info("get menu of the day for restaurant {}", id);
        return restaurantService.getTodayMenu(id);
    }

    public void updateDish(int resId, DishTo dishTo) {
        log.info("update dish with id {}", resId);
        restaurantService.updateDish(resId, dishTo);
    }

    public void deleteDish(int resId, int dishId) {
        log.info("delete dish with id {}", dishId);
        restaurantService.deleteDish(resId, dishId);
    }
}
