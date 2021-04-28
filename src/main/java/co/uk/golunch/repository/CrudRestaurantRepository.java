package co.uk.golunch.repository;

import co.uk.golunch.model.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    //Disable Hibernate 2nd level cache when use Query language. Need this for vote method
    //https://stackoverflow.com/a/7059822
    @Query("SELECT r FROM Restaurant r WHERE r.id=:id")
    Restaurant findById(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.restaurant = null WHERE u.restaurant.id=:resId")
    void cleanVotes(@Param("resId") int resId);
}
