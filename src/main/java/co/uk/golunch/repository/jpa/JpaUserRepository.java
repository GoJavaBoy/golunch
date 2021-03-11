package co.uk.golunch.repository.jpa;

import co.uk.golunch.model.User;
import co.uk.golunch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager em;

//    @Override
//    @Transactional
//    public User update(User user) {
//        return em.merge(user);
//    }

    @Override
    @Transactional
    public User create(User user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }

    @Override
    public boolean delete(int id) {
        return em.createNamedQuery(User.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public User get(int id) {
        return em.find(User.class, id);
    }

    @Override
    public User getByEmail(String email) {
        List<User> users = em.createNamedQuery(User.BY_EMAIL, User.class)
                .setParameter("email", email)
                .getResultList();
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return em.createNamedQuery(User.ALL, User.class).getResultList();
    }
}
