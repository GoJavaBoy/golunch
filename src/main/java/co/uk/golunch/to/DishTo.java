package co.uk.golunch.to;

import co.uk.golunch.HasId;
import co.uk.golunch.model.Dish;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class DishTo implements HasId {

    @NotNull
    private BigDecimal price;

    @NotBlank
    @Size(min = 2, max = 30)
    private String name;

    private Integer id;

    public DishTo() {
    }

    public DishTo(@NotNull BigDecimal price, @NotBlank @Size(min = 2, max = 30) String name, Integer id) {
        this.price = price;
        this.name = name;
        this.id = id;
    }

    public DishTo(@NotNull BigDecimal price, @NotBlank @Size(min = 2, max = 30) String name) {
        this.price = price;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DishTo dishTo = (DishTo) o;
        return Objects.equals(price, dishTo.price) && Objects.equals(name, dishTo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, name);
    }
}
