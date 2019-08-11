package ru.vladzag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.repository.DishRepo;
import ru.vladzag.repository.RestaurantCrudRepo;
import ru.vladzag.repository.RestaurantJpaRepo;
import ru.vladzag.to.RestaurantTo;
import ru.vladzag.util.restaurant.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;


import static ru.vladzag.util.ValidationUtil.checkNotFoundWithId;

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

    public RestaurantTo getWithMenuInDate(int id, LocalDate date){
        Restaurant r =restaurantCrudRepo.getWithMenu(id);
        return RestaurantUtil.getWithFilteredDishes(r,date);
    }

    public Restaurant save(Restaurant r){
        return restaurantCrudRepo.save(r);
    }

    public List<Restaurant> getAll(){
        return restaurantCrudRepo.getAll();
    }

    /*
    public List<Restaurant> getAllInDate(LocalDate date){
        List<Restaurant> restaurants = restaurantCrudRepo.getAll();
        return RestaurantUtil.getInDate(restaurants,date);
    }*/

    public void updateDish(Dish meal, int resId) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFoundWithId(restaurantCrudRepo.saveDish(meal, resId), meal.getId());
    }

    public Dish createDish(Dish meal, int resId) {
        Assert.notNull(meal, "meal must not be null");
        return restaurantCrudRepo.saveDish(meal, resId);
    }

    public void deleteDish(int dishId){
        checkNotFoundWithId(restaurantCrudRepo.deleteDish(dishId), dishId);
    }

    public void delete(int id){
        checkNotFoundWithId(restaurantCrudRepo.delete(id), id);

    }




}
