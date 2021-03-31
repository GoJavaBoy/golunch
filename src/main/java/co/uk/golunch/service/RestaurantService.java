package co.uk.golunch.service;

import co.uk.golunch.model.HistoryRestaurant;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.User;
import co.uk.golunch.repository.RestaurantRepository;
import co.uk.golunch.repository.datajpa.DataJpaHistoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static co.uk.golunch.util.ValidationUtil.*;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final DataJpaHistoryRepository historyRepository;

    public RestaurantService(RestaurantRepository repository, UserService userService, DataJpaHistoryRepository historyRepository) {
        this.restaurantRepository = repository;
        this.userService = userService;
        this.historyRepository = historyRepository;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public boolean delete(int id) {
        Restaurant oldRestaurant = checkNotFoundWithId(restaurantRepository.get(id), id);
        HistoryRestaurant historyRestaurant = new HistoryRestaurant(id, oldRestaurant.getName(),
                oldRestaurant.getVotes(), oldRestaurant.getMenu(), new Date());
        historyRepository.saveInHistory(historyRestaurant);
        return restaurantRepository.delete(id);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @CacheEvict(value = "restaurants", allEntries = true)
    public void update(Restaurant restaurant, int resId) {
        Assert.notNull(restaurant, "meal must not be null");
        Restaurant oldRestaurant = checkNotFoundWithId(restaurantRepository.create(restaurant), restaurant.id());
        HistoryRestaurant historyRestaurant = new HistoryRestaurant(resId, oldRestaurant.getName(),
                oldRestaurant.getVotes(), oldRestaurant.getMenu(), new Date());
        historyRepository.saveInHistory(historyRestaurant);
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

    private boolean canVote(Date lastVote) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime voteDate = lastVote.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return !(currentDate.toLocalDate().equals(voteDate.toLocalDate()) && currentDate.getHour() > 11);
    }
}
