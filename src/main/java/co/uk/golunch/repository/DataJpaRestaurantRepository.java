package co.uk.golunch.repository;

import co.uk.golunch.model.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DataJpaRestaurantRepository {

    private final RestaurantRepository restaurantRepository;

    public DataJpaRestaurantRepository(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public Restaurant create(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant get(int id) {
        return restaurantRepository.findById(id);
    }

    public boolean delete(int id) {
        return restaurantRepository.delete(id) != 0;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

//    public void cleanVotes(int resId) {
//        restaurantRepository.cleanVotes(resId);
//    }
}
