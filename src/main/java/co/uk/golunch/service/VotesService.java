package co.uk.golunch.service;

import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.User;
import co.uk.golunch.model.Vote;
import co.uk.golunch.repository.MenuRepository;
import co.uk.golunch.repository.RestaurantRepository;
import co.uk.golunch.repository.VotesRepository;
import co.uk.golunch.util.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static co.uk.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VotesService {
    private final VotesRepository votesRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public VotesService(VotesRepository votesRepository, RestaurantRepository restaurantRepository, MenuRepository menuRepository) {
        this.votesRepository = votesRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    @Transactional
    public void save(User user, int restaurantId){
        Restaurant restaurant = checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
        if (menuRepository.findAllByRestaurantAndDate(restaurant, LocalDate.now()).isEmpty()){
            throw new NotFoundException("There no menu for this restaurant");
        }
        Optional<Vote> todayVote = votesRepository.getForUserAndDate(user.getId(), LocalDate.now());
        if (todayVote.isEmpty()){
            votesRepository.save(new Vote(user, restaurant, LocalDate.now()));
        } else if (LocalTime.now().isBefore(LocalTime.parse("11:00"))){
            todayVote.get().setRestaurant(restaurant);
        }
    }

    public Vote getTodayVote(User user){
        Assert.notNull(user, "user must not be null");
        return votesRepository.getForUserAndDate(user.getId(), LocalDate.now()).orElse(null);
    }

    public List<Vote> getAllVotes(User user){
        Assert.notNull(user, "user must not be null");
        return votesRepository.findAllByUser(user.getId());
    }
}
