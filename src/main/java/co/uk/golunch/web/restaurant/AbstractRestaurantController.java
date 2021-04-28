package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.HistoryRestaurant;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.web.SecurityUtil;
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

    public Restaurant get(int id) {
        log.info("get restaurant {}", id);
        return restaurantService.get(id);
    }


    public List<Restaurant> getAll() {
        log.info("getAll");
        return restaurantService.getAll();
    }

    public List<HistoryRestaurant> getHistory() {
        log.info("getHistory");
        return restaurantService.getHistory();
    }


    public void delete(int id) {
        log.info("delete {}", id);
        restaurantService.delete(id);
    }

    public void vote(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("user {} vote for restaurant {}", userId, id);
        restaurantService.vote(userId, id);
    }
}
