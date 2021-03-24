package co.uk.golunch.service;

import co.uk.golunch.model.HistoryRestaurant;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.User;
import co.uk.golunch.repository.RestaurantRepository;
import co.uk.golunch.repository.UserRepository;
import co.uk.golunch.repository.datajpa.DataJpaHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static co.uk.golunch.util.ValidationUtil.*;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final DataJpaHistoryRepository historyRepository;

    public RestaurantService(RestaurantRepository repository, UserRepository userRepository, DataJpaHistoryRepository historyRepository) {
        this.restaurantRepository = repository;
        this.userRepository = userRepository;
        this.historyRepository = historyRepository;
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    public boolean delete(int id) {
        Restaurant oldRestaurant = checkNotFoundWithId(restaurantRepository.get(id), id);
        HistoryRestaurant historyRestaurant = new HistoryRestaurant(id, oldRestaurant.getName(),
                oldRestaurant.getVotes().size(), oldRestaurant.getMenu(), new Date());
        historyRepository.saveInHistory(historyRestaurant);

        return restaurantRepository.delete(id);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public void update(Restaurant restaurant, int resId) {
        Assert.notNull(restaurant, "meal must not be null");
        assureIdConsistent(restaurant, resId);

        Restaurant oldRestaurant = checkNotFoundWithId(restaurantRepository.get(resId), resId);
        HistoryRestaurant historyRestaurant = new HistoryRestaurant(resId, oldRestaurant.getName(),
                oldRestaurant.getVotes().size(), oldRestaurant.getMenu(), new Date());
        historyRepository.saveInHistory(historyRestaurant);

        checkNotFoundWithId(restaurantRepository.create(restaurant), restaurant.id());
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "meal must not be null");
        checkNew(restaurant);
        return restaurantRepository.create(restaurant);
    }

    public void vote(int userId, int resId){
        User user = userRepository.get(userId);
        Date lastVote = user.getVoted();
        if (lastVote!=null && !canVote(lastVote)){
            return;
        }
        Restaurant restaurant = get(resId);
        user.setRestaurant(restaurant);
        user.setVoted(new Date());
        userRepository.create(user);
    }

    private boolean canVote(Date lastVote) {
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime voteDate = lastVote.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        return !(currentDate.toLocalDate().equals(voteDate.toLocalDate()) && currentDate.getHour() > 11);
    }
}
