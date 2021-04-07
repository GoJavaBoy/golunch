package co.uk.golunch.repository;

import co.uk.golunch.model.HistoryRestaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudHistoryRepository extends JpaRepository<HistoryRestaurant, Integer> {
}
