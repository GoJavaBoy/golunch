package co.uk.golunch.repository;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RestaurantRepository {

    Restaurant create(Restaurant restaurant);

    // null if not found
    Restaurant get(int id);

    // false if not found
    boolean delete(int id);

    List<Restaurant> getAll();

}
