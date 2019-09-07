package ru.vladzag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.repository.*;
import ru.vladzag.to.RestaurantTo;
import ru.vladzag.util.exception.ScoreAccessException;
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
       return checkNotFoundWithId(restaurantCrudRepo.get(id),id);
    }

    public Dish getDish(int id){return checkNotFoundWithId(restaurantCrudRepo.getDish(id),id);}

    public Restaurant getWithMenu(int id){
        return restaurantCrudRepo.getWithMenu(id);
    }

    @Cacheable("restaurantsTo")
    public RestaurantTo getWithMenuInDate(int id, LocalDate date){
        Restaurant r =restaurantCrudRepo.getWithMenu(id);
        checkNotFoundWithId(r,id);
        return RestaurantUtil.getWithFilteredDishes(r,date);
    }

    public RestaurantTo getWithMenuAndVotesInDate(int id, LocalDate date){
        Restaurant r =restaurantCrudRepo.getWithMenuAndVotes(id);
        checkNotFoundWithId(r,id);
        return RestaurantUtil.getWithFilteredDishesAndCountOfVotes(r,date);
    }

    @Cacheable("score")
    public List<RestaurantTo> getScoreForUser(int userId) throws ScoreAccessException{
        LocalDateTime now = LocalDateTime.now();
        if(vRepo.gerInDateByUser(now.toLocalDate(),userId)!=null)
            if(now.toLocalTime().isAfter(LocalTime.of(11,0)))
                return getAllWithVotes(now.toLocalDate());
            else throw new ScoreAccessException("Results of voting are available after 11 a.m. Now is " + now.toLocalTime());
        else throw new ScoreAccessException("Results are available only for voted users");


    }

    public RestaurantTo getWithVotes(int id, LocalDate date){
        Restaurant r = restaurantCrudRepo.getWithVotes(id);
        checkNotFoundWithId(r,id);
        return RestaurantUtil.getWithFilteredCountOfVotes(r,date);
    }


    public List<RestaurantTo> getAllWithVotes(LocalDate date){
        List<Restaurant> list = restaurantCrudRepo.getAllWithVotes();
        return RestaurantUtil.getAllWithFilteredCountOfVotes(list,date);
    }

    @CacheEvict(value = {"restaurants","restaurantsTo"},allEntries = true)
    public void update(Restaurant r) {
        Assert.notNull(r, "restaurant must not be null");
//      checkNotFoundWithId : check works only for JDBC, disabled
        restaurantCrudRepo.save(r);
    }

    @CacheEvict(value = {"restaurants","restaurantsTo"},allEntries = true)
    public Restaurant create(Restaurant r) {
        Assert.notNull(r, "user must not be null");
        return restaurantCrudRepo.save(r);
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAll(){

        return restaurantCrudRepo.getAll();
    }


    public List<RestaurantTo> getAllToWithCountOfVotes(){
        return RestaurantUtil.getAllWithFilteredCountOfVotesInAllTime(restaurantCrudRepo.getAllWithVotes());
    }

    /*
    public List<Restaurant> getAllInDate(LocalDate date){
        List<Restaurant> restaurants = restaurantCrudRepo.getAll();
        return RestaurantUtil.getInDate(restaurants,date);
    }*/

    @CacheEvict(value = "restaurantsTo",allEntries = true)
    public void updateDish(Dish dish, int resId) {
        Assert.notNull(dish, "meal must not be null");
        checkNotFoundWithId(restaurantCrudRepo.saveDish(dish, resId), dish.getId());
    }

    @CacheEvict(value = "restaurantsTo",allEntries = true)
    public Dish createDish(Dish meal, int resId) {
        Assert.notNull(meal, "meal must not be null");
        return restaurantCrudRepo.saveDish(meal, resId);
    }



    @CacheEvict(value = "restaurantsTo",allEntries = true)
    public void deleteDish(int dishId){
        checkNotFoundWithId(restaurantCrudRepo.deleteDish(dishId), dishId);
    }

    @CacheEvict(value = {"restaurants","restaurantsTo"},allEntries = true)
    public void delete(int id){
        checkNotFoundWithId(restaurantCrudRepo.delete(id), id);

    }

    @CacheEvict(value = "score",allEntries = true)
    public void cacheScoreEvict(){}

    @CacheEvict(value = "restaurants",allEntries = true)
    public void cacheRestaurantsEvict(){}

    @CacheEvict(value = "restaurantsTo",allEntries = true)
    public void cacheRestaurantsToEvict(){}




}
