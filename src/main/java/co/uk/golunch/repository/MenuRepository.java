package co.uk.golunch.repository;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Dish, Integer> {
    List<Dish> findAllByRestaurantAndDate(Restaurant restaurant, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date);
}
