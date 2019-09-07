package ru.vladzag.repository.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.repository.vote.VoteJpaRepo;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RestaurantCrudRepo {

    @Autowired
    VoteJpaRepo voteRepo;

    @Autowired
    RestaurantJpaRepo restaurantJpaRepo;

    @Autowired
    DishJpaRepo dishRepo;

    private final Logger log = LoggerFactory.getLogger(getClass());


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
        log.info("RestaurantCrudRepo getAll() cache check");
        return restaurantJpaRepo.findAll();
    }

    public Restaurant getWithMenu(int id) {
        return restaurantJpaRepo.getWithMenu(id);
    }

    public Restaurant getWithMenuAndVotes(int id) {
        return restaurantJpaRepo.getWithMenuAndVotes(id);
    }

    public Restaurant getWithVotes(int id){return restaurantJpaRepo.getWithVotes(id);}

    public List<Restaurant> getAllWithVotes(){return restaurantJpaRepo.getAllWithVotes();}

    public Dish saveDish(Dish dish, int restId){
        if (!dish.isNew() && getDish(dish.getId()) == null) {
            return null;
        }
        dish.setRestaurant(restaurantJpaRepo.getOne(restId));
        if(dish.getDate()==null)dish.setDate(LocalDate.now());
        return dishRepo.save(dish);
    }

    public boolean deleteDish(int dishId){
        return dishRepo.delete(dishId)!=0;
    }



}
