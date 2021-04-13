package co.uk.golunch.web.restaurant;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.service.RestaurantService;
import co.uk.golunch.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

import static co.uk.golunch.util.ValidationUtil.assureIdConsistent;
import static co.uk.golunch.util.ValidationUtil.checkNew;

//@Controller
//@RequestMapping("/restaurants")
public class JspRestaurantController {
    private static final Logger log = LoggerFactory.getLogger(JspRestaurantController.class);

    private final RestaurantService restaurantService;

    public JspRestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public String createOrUpdate(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        Restaurant restaurant = new Restaurant(request.getParameter("name"));

        Set<Dish> menu = getMenu(request);
        restaurant.setMenu(menu);

        if (StringUtils.hasLength(request.getParameter("id"))) {
            log.info("update restaurant {} with id {}", restaurant, getId(request));
            assureIdConsistent(restaurant, getId(request));
            restaurantService.update(restaurant);
        } else {
            log.info("create restaurant {}", restaurant);
            checkNew(restaurant);
            restaurantService.create(restaurant);
        }
        return "redirect:restaurants";
    }

    @GetMapping
    public String getAll(Model model) {
        log.info("getAll Restaurants");
        model.addAttribute("restaurants", restaurantService.getAll());
        return "restaurants";
    }

    @GetMapping("/delete")
    public String deleteRestaurant(HttpServletRequest request) {
        int id = getId(request);
        log.info("delete restaurant {}", id);
        restaurantService.delete(id);
        return "redirect:/restaurants";
    }

    @GetMapping("/create")
    public String create(Model model) {
        final Restaurant restaurant = new Restaurant("");
        model.addAttribute("restaurant", restaurant);
        return "restaurantForm";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        int id = getId(request);
        final Restaurant restaurant = restaurantService.get(id);
        model.addAttribute("restaurant", restaurant);
        return "restaurantForm";
    }

    @GetMapping("/vote")
    public String vote(HttpServletRequest request) {
        int resId = getId(request);
        int userId = SecurityUtil.authUserId();
        log.info("user {} vote for restaurant {}", userId, resId);
        restaurantService.vote(userId, resId);
        return "redirect:/restaurants";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private Set<Dish> getMenu(HttpServletRequest request) {
        Set<Dish> menu = new HashSet<>();
        Map<String, String[]> dishPriceMap = request.getParameterMap();
        for (int i = 0; i < dishPriceMap.size(); i++) {
            String[] dish = dishPriceMap.get("dish_"+i);
            String[] price = dishPriceMap.get("price_"+i);
            if (dish != null && price != null && !dish[0].isBlank() && !price[0].isBlank()){
                menu.add(new Dish(dish[0], BigDecimal.valueOf(Double.parseDouble(price[0]))));
            }
        }
        return menu;
    }

}
