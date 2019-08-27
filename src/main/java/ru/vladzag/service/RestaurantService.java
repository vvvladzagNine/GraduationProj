package ru.vladzag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.repository.*;
import ru.vladzag.to.RestaurantTo;
import ru.vladzag.util.restaurant.RestaurantUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import static ru.vladzag.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    @Autowired
    RestaurantCrudRepo restaurantCrudRepo;

    @Autowired
    DataJpaUserRepository uRepo;

    @Autowired
    VoteCrudRepo vRepo;

    public Restaurant get(int id){
       return restaurantCrudRepo.get(id);
    }

    public Dish getDish(int id){return restaurantCrudRepo.getDish(id);}

    public Restaurant getWithMenu(int id){
        return restaurantCrudRepo.getWithMenu(id);
    }

    public RestaurantTo getWithMenuInDate(int id, LocalDate date){
        Restaurant r =restaurantCrudRepo.getWithMenu(id);
        return RestaurantUtil.getWithFilteredDishes(r,date);
    }

    public RestaurantTo getWithMenuAndVotesInDate(int id, LocalDate date){
        Restaurant r =restaurantCrudRepo.getWithMenuAndVotes(id);
        return RestaurantUtil.getWithFilteredDishesAndCountOfVotes(r,date);
    }

    public List<RestaurantTo> getScoreForUser(int userId){
        LocalDateTime now = LocalDateTime.now();
        if(vRepo.gerInDateByUser(now.toLocalDate(),userId)!=null && now.toLocalTime().isAfter(LocalTime.of(11,0))){
            return getAllWithVotes(now.toLocalDate());
        }
        else {
            return null;
        }
    }

    public RestaurantTo getWithVotes(int id, LocalDate date){
        Restaurant r = restaurantCrudRepo.getWithVotes(id);
        return RestaurantUtil.getWithFilteredCountOfVotes(r,date);
    }

    public List<RestaurantTo> getAllWithVotes(LocalDate date){
        List<Restaurant> list = restaurantCrudRepo.getAllWithVotes();
        return RestaurantUtil.getAllWithFilteredCountOfVotes(list,date);
    }

    public void update(Restaurant r) {
        Assert.notNull(r, "user must not be null");
//      checkNotFoundWithId : check works only for JDBC, disabled
        restaurantCrudRepo.save(r);
    }


    public Restaurant create(Restaurant r) {
        Assert.notNull(r, "user must not be null");
        return restaurantCrudRepo.save(r);
    }

    public List<Restaurant> getAll(){
        return restaurantCrudRepo.getAll();
    }
    public List<RestaurantTo> getAllTo(){
        return RestaurantUtil.getAllWithFilteredCountOfVotesInAllTime(restaurantCrudRepo.getAllWithVotes());
    }

    /*
    public List<Restaurant> getAllInDate(LocalDate date){
        List<Restaurant> restaurants = restaurantCrudRepo.getAll();
        return RestaurantUtil.getInDate(restaurants,date);
    }*/

    public void updateDish(Dish dish, int resId) {
        Assert.notNull(dish, "meal must not be null");
        checkNotFoundWithId(restaurantCrudRepo.saveDish(dish, resId), dish.getId());
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
