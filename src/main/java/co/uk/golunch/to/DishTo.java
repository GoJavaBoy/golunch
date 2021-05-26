package co.uk.golunch.to;

import java.math.BigDecimal;

public class DishTo {
    private BigDecimal price;
    private String name;

    public DishTo() {
    }

    public DishTo(BigDecimal price, String name) {
        this.price = price;
        this.name = name;
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
