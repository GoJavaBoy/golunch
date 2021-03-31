package co.uk.golunch.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

//@Entity
@Embeddable
//@Table(name = "restaurant_menu")
public class Dish {

  //  @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

  //  @Column(name = "price", nullable = false)
    private BigDecimal price;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "restaurant_id")
//    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(@NotBlank String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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

//    public Restaurant getRestaurant() {
//        return restaurant;
//    }
//
//    public void setRestaurant(Restaurant restaurant) {
//        this.restaurant = restaurant;
//    }


    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
