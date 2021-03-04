package co.uk.golunch.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = Restaurant.GET_ALL, query = "SELECT r FROM Restaurant r"),
        @NamedQuery(name = Restaurant.GET_byID, query = "SELECT r FROM Restaurant r WHERE r.id=:id")
})
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_name_idx")})
public class Restaurant extends AbstractBaseEntity {

    public static final String GET_ALL = "Restaurant.all";
    public static final String GET_byID = "Restaurant.byId";

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "restaurant_menu", joinColumns = @JoinColumn(name = "restaurant_id"))
    @MapKeyColumn(name = "item_map_key")
    @Column(name = "price_map_value")
    private Map<String, BigDecimal> menu;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "restaurant_votes", joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "voted_user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "voted_user_id"}, name = "user_votes_idx")})
    private Set<User> votes;

    public Set<User> getVotes() {
        return votes;
    }

    public void setVotes(Set<User> votes) {
        this.votes = votes;
    }

    public Map<String, BigDecimal> getMenu() {
        return menu;
    }

    public void setMenu(Map<String, BigDecimal> menu) {
        this.menu = menu;
    }
}
