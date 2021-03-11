package co.uk.golunch.repository;

import co.uk.golunch.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

    Restaurant create(Restaurant restaurant);

//    Restaurant update(Restaurant restaurant);

    Restaurant get(int id);

    boolean delete(int id);

    List<Restaurant> getAll();
}
