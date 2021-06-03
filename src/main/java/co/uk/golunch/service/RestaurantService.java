package co.uk.golunch.service;

import co.uk.golunch.model.MenuItem;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.Vote;
import co.uk.golunch.repository.MenuRepository;
import co.uk.golunch.repository.RestaurantRepository;
import co.uk.golunch.repository.VotesRepository;
import co.uk.golunch.to.MenuItemTo;
import co.uk.golunch.to.RestaurantTo;
import co.uk.golunch.util.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static co.uk.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {


    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final VotesRepository votesRepository;

    public RestaurantService(RestaurantRepository restaurantRepository, MenuRepository menuRepository, VotesRepository votesRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.votesRepository = votesRepository;
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @Cacheable("restaurants")
    public List<RestaurantTo> getAllWithMenu() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
       return restaurants.stream()
                .map(restaurant -> new RestaurantTo(restaurant.getName(), menuRepository.findAllByRestaurantAndDate(restaurant, LocalDate.now()).stream()
                         .map(MenuItemTo::new)
                         .collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public RestaurantTo getWithMenuAndVotes(int id) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
        List<MenuItemTo> menu = menuRepository.findAllByRestaurantAndDate(restaurant, LocalDate.now()).stream()
                .map(MenuItemTo::new)
                .collect(Collectors.toList());
        List<Vote> votes = votesRepository.findAllByRestaurantAndDate(restaurant.getId(), LocalDate.now());
        return new RestaurantTo(restaurant.getName(), menu, votes.size());
    }

    public RestaurantTo getWithMenu(int id) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
        List<MenuItemTo> menu = menuRepository.findAllByRestaurantAndDate(restaurant, LocalDate.now()).stream()
                .map(MenuItemTo::new)
                .collect(Collectors.toList());
        return new RestaurantTo(restaurant.getName(), menu);
    }


    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }
}
