package co.uk.golunch;

import co.uk.golunch.model.Restaurant;
import co.uk.golunch.model.Role;
import co.uk.golunch.model.User;
import co.uk.golunch.repository.RestaurantRepository;
import co.uk.golunch.repository.jpa.JpaRestaurantRepository;
import co.uk.golunch.repository.jpa.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestMainClass {

    public static void main(String[] args) {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            JpaRestaurantRepository restaurantRepository = appCtx.getBean(JpaRestaurantRepository.class);
            JpaUserRepository userRepository = appCtx.getBean(JpaUserRepository.class);
//            List<Restaurant> restaurantList = restaurantRepository.getAll();
//            restaurantRepository.checkConnection();
//            System.out.println(restaurantList.isEmpty());
//            System.out.println(restaurantList.size());
//            restaurantList.forEach(System.out::println);
//            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));

            System.out.println("//////////RESTAURANT TEST///////////");
            Restaurant singleResult = restaurantRepository.get(100007);
            System.out.println(singleResult);
            System.out.println(singleResult.getVotes());
            System.out.println(singleResult.getMenu());

            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));

            System.out.println();
            System.out.println("//////////USER TEST///////////");
            User user = userRepository.get(100000);
            System.out.println(user);
            System.out.println(user.getRestaurant());
            System.out.println(userRepository.getAll());
            System.out.println(userRepository.getByEmail("user@yandex.ru"));

            Set<User> testSet = new HashSet<>();

//            singleResult.setVotes(testSet);
            System.out.println(singleResult.getVotes());

        }
    }
}
