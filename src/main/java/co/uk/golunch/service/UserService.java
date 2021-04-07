package co.uk.golunch.service;

import co.uk.golunch.model.User;
import co.uk.golunch.repository.DataJpaUserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static co.uk.golunch.util.ValidationUtil.checkNotFound;
import static co.uk.golunch.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    private final DataJpaUserRepository repository;

    public UserService(DataJpaUserRepository repository) {
        this.repository = repository;
    }

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return repository.create(user);
    }

    @CacheEvict(cacheNames = {"users", "restaurants"}, allEntries = true)
    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Cacheable("users")
    public List<User> getAll() {
        return repository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user) {
        Assert.notNull(user, "user must not be null");
        checkNotFoundWithId(repository.create(user), user.id());
    }

}
