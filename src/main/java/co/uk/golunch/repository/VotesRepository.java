package co.uk.golunch.repository;

import co.uk.golunch.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VotesRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId AND v.date=:date")
    Optional<Vote> getForUserAndDate(@Param("userId") Integer userId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.restaurant.id=:restaurantId AND v.date=:date")
    List<Vote> findAllByRestaurantAndDate(@Param("restaurantId") Integer restaurantId, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId")
    List<Vote> findAllByUser(@Param("userId") Integer userId);
}
