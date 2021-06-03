package co.uk.golunch.to;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class RestaurantTo {

    private String name;
    private List<MenuItemTo> menu;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer votes;

    public RestaurantTo() {
    }

    public RestaurantTo(String name, List<MenuItemTo> menu, Integer votes) {
        this.name = name;
        this.menu = menu;
        this.votes = votes;
    }

    public RestaurantTo(String name, List<MenuItemTo> menu) {
        this.name = name;
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MenuItemTo> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItemTo> menu) {
        this.menu = menu;
    }

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }
}
