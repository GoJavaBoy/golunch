package co.uk.golunch.service;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.Vote;
import co.uk.golunch.repository.DataJpaRestaurantRepository;
import co.uk.golunch.repository.MenuRepository;
import co.uk.golunch.repository.VotesRepository;
import co.uk.golunch.to.DishTo;
import co.uk.golunch.to.RestaurantTo;
import co.uk.golunch.util.exception.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
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

    @CacheEvict(value = "restaurants", allEntries = true)
    @Transactional
    public boolean delete(int id) {
        return restaurantRepository.delete(id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public RestaurantTo getWithMenuAndVotes(int id) {
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

    @Transactional
    public void addMenu(int id, DishTo... dishes) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.get(id), id);
        Assert.notNull(dishes, "menu must not be null");
        if (dishes.length == 0) {
            throw new IllegalArgumentException("menu must not be empty");
        }
        List<Dish> menu = Arrays.stream(dishes)
                .map(dishTo -> new Dish(dishTo, restaurant))
                .collect(Collectors.toList());
        menuRepository.saveAll(menu);
    }

    public List<Dish> getTodayMenu(int id) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.get(id), id);
        return menuRepository.findAllByRestaurantAndDate(restaurant, LocalDate.now());
    }

    @Transactional
    public void updateDish(int resId, DishTo dishTo) {
        Assert.notNull(dishTo, "dish must not be null");
        Dish dish = getDish(resId, dishTo.getId());
        if (!dishTo.isNew() && dish == null) {
            throw new DataIntegrityViolationException("dish must not be new or null");
        }
        dish.setDate(LocalDate.now());
        dish.setName(dishTo.getName());
        dish.setPrice(dishTo.getPrice());
//        menuRepository.save(dish);
    }

    @Transactional
    public void deleteDish(int resId, int dishId) {
        Dish dish = getDish(resId, dishId);
        if (dish == null) {
            throw new NotFoundException("wrong restaurant or dish id");
        }
        menuRepository.deleteById(dishId);
    }

    private Dish getDish(int resId, int dishId) {
        return menuRepository.findByIdAndRestaurantId(dishId, resId);
    }
}
