package ru.vladzag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.vladzag.model.Vote;
import ru.vladzag.repository.VoteCrudRepo;
import ru.vladzag.util.exception.VoteExpiredException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.vladzag.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    @Autowired
    VoteCrudRepo voteCrudRepo;

    public Vote get(int id){
        return voteCrudRepo.get(id);
    }

    public void updateVote(Vote vote,int userId, int resId) throws VoteExpiredException {
        Assert.notNull(vote, "vote must not be null");
        LocalDate dateOfVote = vote.getDate();
        LocalDateTime now = LocalDateTime.now();
        if(now.toLocalDate().equals(dateOfVote) && now.toLocalTime().isBefore(LocalTime.of(11,0))){
            checkNotFoundWithId(voteCrudRepo.save(vote,userId,resId), vote.getId());
        }else throw new VoteExpiredException("Now is "+now.toLocalTime()+", it is too late to vote after 11am");


    }



    public Vote createVote (Vote vote,int userId, int resId) throws VoteExpiredException{
        Assert.notNull(vote, "meal must not be null");
        LocalDate dateOfVote = vote.getDate();
        LocalDateTime now = LocalDateTime.now();
        Vote v = voteCrudRepo.gerInDateByUser(now.toLocalDate(),userId);
        if(v!=null) throw new VoteExpiredException("User already voted today");
        if(now.toLocalDate().equals(dateOfVote)) {
            return voteCrudRepo.save(vote, userId, resId);
        }
        return null;
    }

}
