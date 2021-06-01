package co.uk.golunch.service;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.Vote;
import co.uk.golunch.repository.MenuRepository;
import co.uk.golunch.repository.RestaurantRepository;
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

    public RestaurantTo getWithMenuAndVotes(int id) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
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
        restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @Transactional
    public void addMenu(int id, DishTo... dishes) {
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
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
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
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
    }

    @Transactional
    public void deleteDish(int resId, int dishId) {
        Dish dish = getDish(resId, dishId);
        if (dish == null) {
            throw new NotFoundException("wrong restaurant or dish id");
        }
        menuRepository.deleteById(dishId);
    }

    private Dish getDish(int resId, Integer dishId) {
        if (dishId == null){
            throw new DataIntegrityViolationException("provide id when updating dish");
        }
        return menuRepository.findByIdAndRestaurantId(dishId, resId);
    }
}
