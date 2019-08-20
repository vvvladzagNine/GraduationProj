package ru.vladzag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.vladzag.model.Vote;
import ru.vladzag.repository.CrudUserRepository;
import ru.vladzag.repository.RestaurantJpaRepo;
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

    @Autowired
    CrudUserRepository uRepo;

    @Autowired
    RestaurantJpaRepo rRepo;

    public Vote get(int id){
        return voteCrudRepo.get(id);
    }

    public void updateVote(int voteId, int userId,int resId) throws VoteExpiredException {
        //Assert.notNull(vote, "vote must not be null");
        //LocalDate dateOfVote = vote.getDate();
        Vote v = voteCrudRepo.get(voteId);
        LocalDate dateOfVote = v.getDate();
        LocalDateTime now = LocalDateTime.now();
        if(
                v.getVoter().getId() == userId &&
                now.toLocalDate().equals(dateOfVote) &&
                now.toLocalTime().isBefore(LocalTime.of(11,0)))
        {
            checkNotFoundWithId(voteCrudRepo.update(v,resId), v.getId());
        }else throw new VoteExpiredException("Now is " + now.toLocalTime()+", it is too late to vote ");


    }

    public Vote createVote (int userId, int resId) throws VoteExpiredException{

        LocalDateTime now = LocalDateTime.now();
        Vote v = new Vote();

        Vote v2 = voteCrudRepo.gerInDateByUser(now.toLocalDate(),userId);
        if(v2!=null) throw new VoteExpiredException("User has already voted today");
        {
            v.setDate(now.toLocalDate());
            return voteCrudRepo.save(v, userId, resId);
        }

    }

}
