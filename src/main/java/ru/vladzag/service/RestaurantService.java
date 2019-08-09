package ru.vladzag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vladzag.model.Restaurant;
import ru.vladzag.repository.RestaurantCrudRepo;
import ru.vladzag.repository.RestaurantJpaRepo;
import ru.vladzag.util.restaurant.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    RestaurantCrudRepo restaurantCrudRepo;

    public Restaurant get(int id){
       return restaurantCrudRepo.get(id);
    }

    public Restaurant getWithMenu(int id){
        return restaurantCrudRepo.getWithMenu(id);
    }

    public Restaurant save(Restaurant r){
        return restaurantCrudRepo.save(r);
    }

    public List<Restaurant> getAllInDate(LocalDate date){
        List<Restaurant> restaurants = restaurantCrudRepo.getAll();
        return RestaurantUtil.getInDate(restaurants,date);
    }



}
