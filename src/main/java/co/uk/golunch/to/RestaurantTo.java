package co.uk.golunch.to;

import co.uk.golunch.model.Vote;

import java.util.List;

public class RestaurantTo {

    private String name;
    private List<DishTo> menu;
    private Integer votes;

    public RestaurantTo() {
    }

    public RestaurantTo(String name, List<DishTo> menu, Integer votes) {
        this.name = name;
        this.menu = menu;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DishTo> getMenu() {
        return menu;
    }

    public void setMenu(List<DishTo> menu) {
        this.menu = menu;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
