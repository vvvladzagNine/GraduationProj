package ru.vladzag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RestaurantCrudRepo {

    @Autowired
    VoteRepo voteRepo;

    @Autowired
    RestaurantJpaRepo restaurantJpaRepo;

    @Autowired
    DishRepo dishRepo;



    public Dish getDish(int id){
        return dishRepo.findById(id).orElse(null);
    }

    public Restaurant save(Restaurant r) {
        return restaurantJpaRepo.save(r);
    }


    public boolean delete(int id) {
        return restaurantJpaRepo.delete(id)!=0;
    }


    public Restaurant get(int id) {
        return restaurantJpaRepo.findById(id).orElse(null);
    }

    public List<Restaurant> getAll() {
        return restaurantJpaRepo.findAll();
    }

    public Restaurant getWithMenu(int id) {
        return restaurantJpaRepo.getWithMenu(id);
    }



    public Dish saveDish(Dish dish, int restId){
        if (!dish.isNew() && getDish(dish.getId()) == null) {
            return null;
        }
        dish.setRestaurant(restaurantJpaRepo.getOne(restId));
        return dishRepo.save(dish);
    }

    public boolean deleteDish(int dishId){
        return dishRepo.delete(dishId)!=0;
    }



}
