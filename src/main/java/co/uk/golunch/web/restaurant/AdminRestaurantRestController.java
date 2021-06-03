package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.to.DishTo;
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

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void update(@PathVariable int id, @RequestBody @Valid Restaurant restaurant) {
        super.update(id, restaurant);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody @Valid Restaurant restaurant) {
        Restaurant created = super.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping("/{id}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public void addMenu(@PathVariable int id, @RequestBody @Valid DishTo... menu) {
        super.addMenu(id, menu);
    }

    @GetMapping("/{id}/menu")
    @Override
    public List<Dish> getTodayMenu(@PathVariable int id) {
       return super.getTodayMenu(id);
    }

    @PutMapping("/{id}/menu")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void updateDish(@PathVariable int id, @RequestBody @Valid DishTo dishTo){
        super.updateDish(id, dishTo);
    }

    @DeleteMapping("/{id}/menu/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void deleteDish(@PathVariable int id, @PathVariable int dishId){
        super.deleteDish(id, dishId);
    }
}
