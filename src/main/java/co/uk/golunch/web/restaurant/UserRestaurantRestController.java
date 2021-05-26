package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.Restaurant;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantRestController extends AbstractRestaurantController{

    static final String REST_URL = "/restaurants";

    @GetMapping
    @Override
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @GetMapping("/{id}")
    @Override
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @PatchMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void vote(@PathVariable int id) {
        super.vote(id);
    }
}
