package co.uk.golunch.service;

import co.uk.golunch.repository.jpa.JpaRestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    private final JpaRestaurantRepository repository;

    public RestaurantService(JpaRestaurantRepository repository) {
        this.repository = repository;
    }
}
