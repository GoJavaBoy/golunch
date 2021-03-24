package co.uk.golunch.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Entity
@Table(name = "restaurants_history")
public class HistoryRestaurant extends AbstractBaseEntity{

    private int votes;

    private Date saved;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "restaurant_menu_history", joinColumns = @JoinColumn(name = "restaurant_id"))
    @MapKeyColumn(name = "item_map_key")
    @Column(name = "price_map_value")
    private Map<String, BigDecimal> menu;

    public HistoryRestaurant() {
    }

    public HistoryRestaurant(Integer id, String name, int votes, Map<String, BigDecimal> menu, Date date) {
        super(id, name);
        this.votes = votes;
        this.menu = menu;
        this.saved = date;
    }
}
