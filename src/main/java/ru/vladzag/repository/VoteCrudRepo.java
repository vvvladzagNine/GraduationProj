package ru.vladzag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Vote;
import ru.vladzag.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VoteCrudRepo {


    @Autowired
    VoteRepo voteRepo;

    @Autowired
    RestaurantJpaRepo resRep;

    @Autowired
    CrudUserRepository userRep;


    public Vote get(int id) {
        return voteRepo.findById(id).orElse(null);
    }

    List<Vote> getInDate(LocalDate date){
        return voteRepo.getInDate(date);
    }

    public boolean delete(int id){
        return voteRepo.delete(id)!=0;
    }


    public Vote update(Vote vote, int resId ){
        if (vote.isNew()) {
            return null;
        }
        vote.setElected(resRep.getOne(resId));
        //vote.setVoter(userRep.getOne(userId));
        return voteRepo.save(vote);
    }

    public Vote save(Vote vote, int userId, int resId ){
        if (!vote.isNew()) {
            return null;
        }
        vote.setElected(resRep.getOne(resId));
        vote.setVoter(userRep.getOne(userId));
        return voteRepo.save(vote);
    }

    public Vote gerInDateByUser(LocalDate date,int userId){
        return voteRepo.getInDateByUser(date,userId);
    }

    public List<Vote> getVotesByUser(int userId){
        return voteRepo.getVotesByUser(userId);

    }

}
