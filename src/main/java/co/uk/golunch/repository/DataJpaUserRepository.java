package co.uk.golunch.repository;

import co.uk.golunch.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DataJpaUserRepository {

    private final CrudJpaUserRepository crudJpaUserRepository;

    public DataJpaUserRepository(CrudJpaUserRepository crudJpaUserRepository) {
        this.crudJpaUserRepository = crudJpaUserRepository;
    }

    @Transactional
    public User save(User user) {
        return crudJpaUserRepository.save(user);
    }

    public boolean delete(int id) {
        return crudJpaUserRepository.delete(id) != 0;
    }

    public User get(int id) {
        return crudJpaUserRepository.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return crudJpaUserRepository.getByEmail(email);
    }

    public List<User> getAll() {
        return crudJpaUserRepository.findAll();
    }

    public User getWithRestaurant(int id){
       return crudJpaUserRepository.getWithRestaurant(id);
    }
}
