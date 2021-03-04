package co.uk.golunch.repository;

import co.uk.golunch.model.User;

import java.util.List;

public interface UserRepository {

    // null if not found
    User update(User user);

    User create(User user);

    // false if not found
    boolean delete(int id);

    // null if not found
    User get(int id);

    // null if not found
    User getByEmail(String email);

    List<User> getAll();
}
