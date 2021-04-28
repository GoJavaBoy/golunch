package co.uk.golunch.repository;

import co.uk.golunch.model.HistoryRestaurant;
import co.uk.golunch.model.Restaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DataJpaHistoryRepository {

    private final CrudHistoryRepository crudHistoryRepository;

    public DataJpaHistoryRepository(CrudHistoryRepository crudHistoryRepository) {
        this.crudHistoryRepository = crudHistoryRepository;
    }

    @Transactional
    public void saveInHistory(HistoryRestaurant restaurant){
        if (restaurant == null || restaurant.isNew()){
            return;
        }
        crudHistoryRepository.save(restaurant);
    }

    public List<HistoryRestaurant> getAll() {
        return crudHistoryRepository.findAll();
    }
}
