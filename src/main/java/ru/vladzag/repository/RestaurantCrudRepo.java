package ru.vladzag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.vladzag.model.Restaurant;
import ru.vladzag.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public class RestaurantCrudRepo implements RestaurantRepo {

    @Autowired
    VoteRepo voteRepo;

    @Autowired
    RestaurantJpaRepo restaurantJpaRepo;


    @Override
    public Restaurant save(Restaurant r) {
        return restaurantJpaRepo.save(r);
    }

    @Override
    public boolean delete(int id) {
        return restaurantJpaRepo.delete(id)!=0;
    }

    @Override
    public Restaurant get(int id) {
        return restaurantJpaRepo.getById(id);
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantJpaRepo.findAll();
    }

    @Override
    public Restaurant getWithMenu(int id) {
        return restaurantJpaRepo.getWithMenu(id);
    }

    public Vote saveVote(Vote vote){
        return voteRepo.save(vote);
    }

    public List<Vote> votesInDate(LocalDate localDate){
        return voteRepo.getInDate(localDate);
    }

}
