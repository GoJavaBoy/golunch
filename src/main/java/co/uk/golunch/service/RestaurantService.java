package co.uk.golunch.service;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.Vote;
import co.uk.golunch.repository.DataJpaRestaurantRepository;
import co.uk.golunch.repository.MenuRepository;
import co.uk.golunch.repository.VotesRepository;
import co.uk.golunch.to.DishTo;
import co.uk.golunch.to.RestaurantTo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static co.uk.golunch.util.ValidationUtil.*;

@Service
public class RestaurantService {

    private final DataJpaRestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final VotesRepository votesRepository;

    public RestaurantService(DataJpaRestaurantRepository restaurantRepository, MenuRepository menuRepository, VotesRepository votesRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.votesRepository = votesRepository;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public boolean delete(int id) {
        return restaurantRepository.delete(id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public RestaurantTo getWithMenuAndVotes(int id){
        //LocalDate.now()
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.get(id), id);
        List<DishTo> menu = menuRepository.findAllByRestaurantAndDate(restaurant, LocalDate.now()).stream()
                .map(DishTo::new)
                .collect(Collectors.toList());
        List<Vote> votes = votesRepository.findAllByRestaurantAndAndDate(restaurant.getId(), LocalDate.now());
        return new RestaurantTo(restaurant.getName(), menu, votes.size());
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        restaurantRepository.create(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.create(restaurant);
    }

}
