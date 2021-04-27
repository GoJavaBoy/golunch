package co.uk.golunch.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "restaurants_history")
public class HistoryRestaurant extends AbstractBaseEntity {

    @Column(name = "votes")
    private Integer votes;

    @Column(name = "saved", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date saved;

    @ElementCollection
    @CollectionTable(name="restaurant_menu_history", joinColumns = @JoinColumn(name = "restaurant_id"))
    @AttributeOverrides({
            @AttributeOverride(name="name",
                    column=@Column(name="name")),
            @AttributeOverride(name="price",
                    column=@Column(name="price"))
    })
    private Set<Dish> menu;

    public HistoryRestaurant() {
    }

    public HistoryRestaurant(Integer id, String name, int votes, Set<Dish> menu, Date date) {
        super(id, name);
        this.votes = votes;
        this.menu = menu;
        this.saved = date;
    }
}
