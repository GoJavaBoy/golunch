package co.uk.golunch.repository.datajpa;

import co.uk.golunch.model.HistoryRestaurant;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaHistoryRepository {

    private final CrudHistoryRepository crudHistoryRepository;

    public DataJpaHistoryRepository(CrudHistoryRepository crudHistoryRepository) {
        this.crudHistoryRepository = crudHistoryRepository;
    }

    public void saveInHistory(HistoryRestaurant restaurant){
        if (restaurant == null || restaurant.isNew()){
            return;
        }
        crudHistoryRepository.save(restaurant);
    }
}
