package co.uk.golunch.web;


import co.uk.golunch.model.Restaurant;
import co.uk.golunch.repository.RestaurantRepository;
import co.uk.golunch.service.RestaurantService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class RestaurantServlet extends HttpServlet {

    private ClassPathXmlApplicationContext springContext;
    private RestaurantService restaurantService;

    @Override
    public void init() {
        springContext = new ClassPathXmlApplicationContext();
        springContext.setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
        springContext.refresh();
        restaurantService = springContext.getBean(RestaurantService.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        Restaurant restaurant = new Restaurant(req.getParameter("name"));
        req.getParameterMap().forEach((k, v)-> System.out.println(k+" "+ Arrays.toString(v)));

        Map<String, BigDecimal> menu = getMenu(req);
        restaurant.setMenu(menu);

        if (StringUtils.hasLength(req.getParameter("id"))) {
            restaurantService.update(restaurant, getId(req));
        } else {
            restaurantService.create(restaurant);
        }
        resp.sendRedirect("restaurants");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete" -> {
                int id = getId(req);
                restaurantService.delete(id);
                resp.sendRedirect("restaurants");
            }
//            case "vote" -> {
//                int id = getId(req);
//
//                resp.sendRedirect("restaurants");
//            }
            case "create", "update" -> {
                final Restaurant restaurant = "create".equals(action) ?
                        new Restaurant() :
                        restaurantService.get(getId(req));
                req.setAttribute("restaurant", restaurant);
                req.getRequestDispatcher("/restaurantForm.jsp").forward(req, resp);
            }
            default -> {
                req.setAttribute("restaurants",  restaurantService.getAll());
                req.getRequestDispatcher("/restaurants.jsp").forward(req, resp);
            }
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private Map<String, BigDecimal> getMenu(HttpServletRequest request) {
        Map<String, BigDecimal> menu = new HashMap<>();
        Map<String, String[]> dishPriceMap = request.getParameterMap();
        for (int i = 0; i < dishPriceMap.size(); i++) {
            String[] dish = dishPriceMap.get("dish_"+i);
            String[] price = dishPriceMap.get("price_"+i);
            if (dish != null && price != null && !dish[0].isBlank() && !price[0].isBlank()){
                menu.put(dish[0], BigDecimal.valueOf(Double.parseDouble(price[0])));
            }
        }
        return menu;
    }
}
