package co.uk.golunch;

import co.uk.golunch.model.Dish;
import co.uk.golunch.model.Restaurant;
import co.uk.golunch.service.RestaurantService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class TestMainClass {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            RestaurantService restaurantService = appCtx.getBean(RestaurantService.class);
           // JpaUserRepository userRepository = appCtx.getBean(JpaUserRepository.class);
//            List<Restaurant> restaurantList = restaurantRepository.getAll();
//            restaurantRepository.checkConnection();
//            System.out.println(restaurantList.isEmpty());
//            System.out.println(restaurantList.size());
//            restaurantList.forEach(System.out::println);
//            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));

//            System.out.println("//////////RESTAURANT TEST///////////");
//            Restaurant singleResult = restaurantRepository.get(100007);
//            System.out.println(singleResult);
//            System.out.println(singleResult.getVotes());
//            System.out.println(singleResult.getMenu());
//
//            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
//
//            System.out.println();
//            System.out.println("//////////USER TEST///////////");
//            User user = userRepository.get(100000);
//            System.out.println(user);
//            System.out.println(user.getRestaurant());
//            System.out.println(userRepository.getAll());
//            System.out.println(userRepository.getByEmail("user@yandex.ru"));
//
//            Set<User> testSet = new HashSet<>();
//
////            singleResult.setVotes(testSet);
//            System.out.println(singleResult.getVotes());

//            //Достали юзера
//            User user = userRepository.get(100009);
//            System.out.println(user);
//            System.out.println();
//
//            //Достали ресторан за который он голосовал
//            Restaurant restaurant = user.getRestaurant();
//            System.out.println(restaurant);
//            System.out.println();
//
//            //Достали всех юзеров которые проголосовали за этот ресторан
//            Set<User> votes = restaurant.getVotes();
//            System.out.println("VOTES");
//            votes.forEach(user1 -> System.out.println(user1));
//            System.out.println();
//
//            //Достали меню этого ресторана
//            Map<String, BigDecimal> menu = restaurant.getMenu();
//            menu.forEach((k, v) -> System.out.println(k+" "+v));

//            restaurantService.create(new Restaurant(null, "Zima"));
//            System.out.println(restaurantService.get(100012).getVotes());
            Restaurant restaurant = restaurantService.get(100000);
            System.out.println(restaurant);
            System.out.println(restaurant.getVotes());
        }
    }
}
