package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.Restaurant;
import co.uk.golunch.to.RestaurantTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/withMenu")
    @Override
    public List<RestaurantTo> getAllWithMenu() {
        return super.getAllWithMenu();
    }

    @GetMapping("/{id}")
    @Override
    public RestaurantTo getWithMenu(@PathVariable int id) {
        return super.getWithMenu(id);
    }


}
