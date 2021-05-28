package co.uk.golunch.to;

import co.uk.golunch.HasId;
import co.uk.golunch.model.Dish;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class DishTo implements HasId {

    @NotNull
    private BigDecimal price;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    private Integer id;

    public DishTo() {
    }

    public DishTo(Dish dish) {
        this.id = dish.getId();
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
