package co.uk.golunch.web.user;

import co.uk.golunch.repository.RestaurantRepository;
import org.springframework.stereotype.Controller;

@Controller
public class AdminRestController {

    private final RestaurantRepository repository;

    public AdminRestController(RestaurantRepository repository) {
        this.repository = repository;
    }



}
