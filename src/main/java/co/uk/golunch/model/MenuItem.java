package co.uk.golunch.model;

import co.uk.golunch.to.MenuItemTo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "menu", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "name"}, name = "menu_item_name_idx")})
public class MenuItem extends AbstractNamedEntity {

    @NotNull
    @NumberFormat
    private BigDecimal price;

    @NotNull
    @Column(name = "menu_item_add_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public MenuItem() {
    }

    public MenuItem(Integer id, String name, BigDecimal price, LocalDate date, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.date = date;
        this.restaurant = restaurant;
    }

    public MenuItem(MenuItemTo menuItemTo, Restaurant restaurant) {
        super(menuItemTo.getId(), menuItemTo.getName());
        this.price = menuItemTo.getPrice();
        this.date = LocalDate.now();
        this.restaurant = restaurant;
    }

    public MenuItem(MenuItem menuItem) {
        this(menuItem.getId(), menuItem.getName(), menuItem.getPrice(), menuItem.getDate(), menuItem.getRestaurant());
    }

    public MenuItem(MenuItemTo menuItemTo) {
        super(menuItemTo.getId(), menuItemTo.getName());
        this.price = menuItemTo.getPrice();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return Objects.equals(name, menuItem.name) && Objects.equals(price, menuItem.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
