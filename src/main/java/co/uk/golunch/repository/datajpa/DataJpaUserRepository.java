package co.uk.golunch.repository.datajpa;

import co.uk.golunch.model.User;
import co.uk.golunch.repository.UserRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class DataJpaUserRepository implements UserRepository {

    private final CrudJpaUserRepository crudJpaUserRepository;

    public DataJpaUserRepository(CrudJpaUserRepository crudJpaUserRepository) {
        this.crudJpaUserRepository = crudJpaUserRepository;
    }

    @Override
    @Transactional
    public User create(User user) {
        return crudJpaUserRepository.save(user);
    }

    @Override
    public boolean delete(int id) {
        return crudJpaUserRepository.delete(id) != 0;
    }

    @Override
    public User get(int id) {
        return crudJpaUserRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudJpaUserRepository.getByEmail(email);
    }

    @Override
    public List<User> getAll() {
        return crudJpaUserRepository.findAll();
    }
}
