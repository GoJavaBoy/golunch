package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.Restaurant;
import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.*;

import static co.uk.golunch.util.ValidationUtil.assureIdConsistent;
import static co.uk.golunch.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/restaurants";
    private static final Logger log = LoggerFactory.getLogger(JspRestaurantController.class);

    private final RestaurantService restaurantService;

    public RestaurantRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @RequestBody Restaurant restaurant) {
            log.info("update restaurant {} with id {}", restaurant, id);
            assureIdConsistent(restaurant, id);
            restaurantService.update(restaurant, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantService.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return restaurantService.get(id);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("getAll Restaurants");
        return restaurantService.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant {}", id);
        restaurantService.delete(id);
    }

    @PutMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int id) {
        int userId = SecurityUtil.authUserId();
        log.info("user {} vote for restaurant {}", userId, id);
        restaurantService.vote(userId, id);
    }

//    @PostMapping("/{id}/menu")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void addDish(@PathVariable int id, @RequestBody Dish... dishes){
//        log.info("add dish for restaurant {}", id);
//        restaurantService.addDish(id, dishes);
//    }

}
