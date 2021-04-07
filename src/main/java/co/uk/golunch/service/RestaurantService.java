package co.uk.golunch.service;

import co.uk.golunch.model.HistoryRestaurant;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.User;
import co.uk.golunch.repository.DataJpaHistoryRepository;
import co.uk.golunch.repository.DataJpaRestaurantRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static co.uk.golunch.util.ValidationUtil.*;

@Service
public class RestaurantService {

    private final DataJpaRestaurantRepository restaurantRepository;
    private final UserService userService;
    private final DataJpaHistoryRepository historyRepository;

    public RestaurantService(DataJpaRestaurantRepository restaurantRepository, UserService userService, DataJpaHistoryRepository historyRepository) {
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.historyRepository = historyRepository;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public boolean delete(int id) {
        Restaurant oldRestaurant = checkNotFoundWithId(restaurantRepository.get(id), id);
        saveInHistory(oldRestaurant);
        return restaurantRepository.delete(id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant, int resId) {
        Assert.notNull(restaurant, "restaurant must not be null");
        Restaurant oldRestaurant = checkNotFoundWithId(restaurantRepository.get(resId), resId);
        saveInHistory(oldRestaurant);
        restaurantRepository.cleanVotes(resId);
        restaurantRepository.create(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "meal must not be null");
        return restaurantRepository.create(restaurant);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void vote(int userId, int resId) {
        User user = userService.get(userId);
        Date lastVote = user.getVoted();
        if (lastVote != null && !canVote(lastVote)) {
            return;
        }
        Restaurant restaurant = get(resId);
        user.setRestaurant(restaurant);
        user.setVoted(new Date());
        userService.update(user);
    }



//    @CacheEvict(value = "restaurants", allEntries = true)
//    public void addDish(int id, Dish... dishes){
//        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.get(id), id);
//        Assert.notNull(dishes, "dishes must not be null");
//        Set<Dish> menu = restaurant.getMenu();
//        menu.addAll(Arrays.asList(dishes));
//        saveInHistory(restaurant);
//        restaurantRepository.create(restaurant);
//    }

    private boolean canVote(Date lastVote) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime voteDate = lastVote.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return !(currentDate.toLocalDate().equals(voteDate.toLocalDate()) && currentDate.getHour() > 11);
    }

    private void saveInHistory(Restaurant restaurant){
        HistoryRestaurant historyRestaurant = new HistoryRestaurant(restaurant.getId(), restaurant.getName(),
                restaurant.getVotes(), restaurant.getMenu(), new Date());
        historyRepository.saveInHistory(historyRestaurant);
    }
}
