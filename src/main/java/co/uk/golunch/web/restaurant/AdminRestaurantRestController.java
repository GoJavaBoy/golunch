package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.HistoryRestaurant;
import co.uk.golunch.model.Restaurant;
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

    static final String REST_URL = "/restaurants";

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

    @GetMapping("/{id}")
    @Override
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping
    @Override
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("/history")
    @Override
    public List<HistoryRestaurant> getHistory() {
        return super.getHistory();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PatchMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void vote(@PathVariable int id) {
        super.vote(id);
    }

}
