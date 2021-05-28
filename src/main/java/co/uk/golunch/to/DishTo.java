package co.uk.golunch.to;

import co.uk.golunch.model.Dish;

import java.math.BigDecimal;

public class DishTo {
    private BigDecimal price;
    private String name;

    public DishTo() {
    }

    public DishTo(Dish dish) {
        this.price = dish.getPrice();
        this.name = dish.getName();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
