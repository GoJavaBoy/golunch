package co.uk.golunch.repository;

import co.uk.golunch.model.User;

public interface UserRepository {

    User update(User user);

    boolean delete(int id);
}
