package co.uk.golunch.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import javax.persistence.*;
import java.util.Set;
import org.hibernate.annotations.Cache;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_name_idx")})
public class Restaurant extends AbstractNamedEntity {

   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name="restaurant_menu", joinColumns = @JoinColumn(name = "restaurant_id"))
   @AttributeOverrides({
           @AttributeOverride(name="name",
                   column=@Column(name="name")),
           @AttributeOverride(name="price",
                   column=@Column(name="price"))
   })
    private Set<Dish> menu;

//    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
//    private Set<User> votesWithUser;

    //Counting votes without initialize votesWithUser
    @Formula("(select count(*) from users where users.restaurant_id = id)")
    private int votes;

    public Restaurant(Restaurant restaurant){
        this(restaurant.getId(), restaurant.getName());
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(String name) {
        super(null, name);
    }

    public Restaurant() {
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Set<Dish> getMenu() {
        return menu;
    }

    public void setMenu(Set<Dish> menu) {
        this.menu = menu;
    }
}
