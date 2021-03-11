package co.uk.golunch.service;

import co.uk.golunch.model.Restaurant;
import co.uk.golunch.repository.RestaurantRepository;
import co.uk.golunch.repository.jpa.JpaRestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static co.uk.golunch.util.ValidationUtil.*;

@Service
public class RestaurantService {

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant get(int id) {
        return repository.get(id);
    }

    public boolean delete(int id) {
        return repository.delete(id);
    }

    public List<Restaurant> getAll() {
        return repository.getAll();
    }

    public void update(Restaurant restaurant, int resId) {
        Assert.notNull(restaurant, "meal must not be null");
        assureIdConsistent(restaurant, resId);
        checkNotFoundWithId(repository.create(restaurant), restaurant.id());
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "meal must not be null");
        checkNew(restaurant);
        return repository.create(restaurant);
    }
}
