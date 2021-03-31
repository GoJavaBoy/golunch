package co.uk.golunch.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;


@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurant_name_idx")})
public class Restaurant extends AbstractNamedEntity {


//    @ElementCollection(fetch = FetchType.EAGER)
//    @CollectionTable(name = "restaurant_menu", joinColumns = @JoinColumn(name = "restaurant_id"))
//    @MapKeyColumn(name = "item_map_key")
//    @Column(name = "price_map_value")
//    private Map<String, BigDecimal> menu;

   // @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
   @ElementCollection(fetch = FetchType.EAGER)
   @CollectionTable(name="restaurant_menu", joinColumns = @JoinColumn(name = "restaurant_id"))
   @AttributeOverrides({
           @AttributeOverride(name="name",
                   column=@Column(name="name")),
           @AttributeOverride(name="price",
                   column=@Column(name="price"))
   })
    private Set<Dish> menu;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER)
//    @JoinTable(name = "restaurant_votes", joinColumns = @JoinColumn(name = "restaurant_id"),
//            inverseJoinColumns = @JoinColumn(name = "voted_user_id"),
//            uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "voted_user_id"}, name = "user_votes_idx")})
    private Set<User> votes;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(String name) {
        super(null, name);
    }

    public Restaurant() {
    }

    public Set<User> getVotedUsers() {
        return votes;
    }

    public int getVotes() {
        return votes == null ? 0 : votes.size();
    }

    public void setVotes(Set<User> votes) {
        this.votes = votes;
    }

    public void addVote(User user){
        this.votes.add(user);
    }

    public void removeVote(User user){
        this.votes.remove(user);
    }

    public Set<Dish> getMenu() {
        return menu;
    }

    public void setMenu(Set<Dish> menu) {
        this.menu = menu;
    }
}
