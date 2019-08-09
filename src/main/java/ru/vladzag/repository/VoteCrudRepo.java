package ru.vladzag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Vote;

import java.time.LocalDate;
import java.util.List;

@Repository
public class VoteCrudRepo {


    @Autowired
    VoteRepo voteRepo;


    public Vote get(int id) {
        return voteRepo.findById(id).orElse(null);
    }

    List<Vote> getInDate(LocalDate date){
        return voteRepo.getInDate(date);
    }

    boolean delete(int id){
        return voteRepo.delete(id)!=0;
    }

    public Vote save(Vote vote, int id){
        if (!vote.isNew() && get(vote.getId()) == null) {
            return null;
        }
        return voteRepo.save(vote);
    }

}
