package co.uk.golunch.repository.jpa;

import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.User;
import co.uk.golunch.repository.RestaurantRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaRestaurantRepository implements RestaurantRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public Restaurant create(Restaurant restaurant) {
        if (restaurant.isNew()) {
            em.persist(restaurant);
            return restaurant;
        } else {
            return em.merge(restaurant);
        }
    }

//    @Override
//    public Restaurant update(Restaurant restaurant) {
//        return null;
//    }

    @Override
    public Restaurant get(int id) {
        return em.find(Restaurant.class, id);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return em.createNamedQuery(Restaurant.DELETE)
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    public List<Restaurant> getAll() {
        return em.createNamedQuery(Restaurant.GET_ALL, Restaurant.class)
                .getResultList();
    }
}
