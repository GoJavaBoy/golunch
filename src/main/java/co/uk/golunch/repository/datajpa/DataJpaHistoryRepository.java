package co.uk.golunch.repository.datajpa;

import co.uk.golunch.model.HistoryRestaurant;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
