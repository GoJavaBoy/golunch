package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.MenuItem;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.to.MenuItemTo;
import co.uk.golunch.to.RestaurantTo;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantRestController extends AbstractRestaurantController {

    static final String REST_URL = "/admin/restaurants";

    @PutMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void update(@PathVariable int restaurantId, @RequestBody @Valid Restaurant restaurant) {
        super.update(restaurantId, restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody @Valid Restaurant restaurant) {
        Restaurant created = super.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/{restaurantId}")
    @Override
    public RestaurantTo getWithMenuAndVotes(@PathVariable int restaurantId) {
        return super.getWithMenuAndVotes(restaurantId);
    }

    @DeleteMapping("/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable int restaurantId) {
        super.delete(restaurantId);
    }
}
