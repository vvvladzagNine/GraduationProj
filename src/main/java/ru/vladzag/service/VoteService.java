package ru.vladzag.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vladzag.model.Vote;
import ru.vladzag.repository.CrudUserRepository;
import ru.vladzag.repository.RestaurantJpaRepo;
import ru.vladzag.repository.VoteCrudRepo;
import ru.vladzag.to.RestaurantTo;
import ru.vladzag.to.VoteTo;
import ru.vladzag.util.exception.VoteException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.vladzag.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    final
    VoteCrudRepo voteCrudRepo;

    final
    CrudUserRepository uRepo;

    final
    RestaurantJpaRepo rRepo;

    final
    RestaurantService restaurantService;

    public VoteService(VoteCrudRepo voteCrudRepo, CrudUserRepository uRepo, RestaurantJpaRepo rRepo, RestaurantService restaurantService) {
        this.voteCrudRepo = voteCrudRepo;
        this.uRepo = uRepo;
        this.rRepo = rRepo;
        this.restaurantService = restaurantService;
    }


    public Vote get(int id){
        return voteCrudRepo.get(id);
    }

    @CacheEvict(value = "votes",allEntries = true)
    public void updateVote(int voteId, int userId,int resId) throws VoteException {
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
        }else throw new VoteException("Now is " + now.toLocalTime()+", it is too late to change your mind ");


    }

    @CacheEvict(value = "votes",allEntries = true)
    public Vote createVote (int userId, int resId) throws VoteException {

        LocalDateTime now = LocalDateTime.now();
        Vote v = new Vote();
        Vote v2 = voteCrudRepo.gerInDateByUser(now.toLocalDate(),userId);
        if(v2!=null) throw new VoteException("User has already voted today");

        v.setDate(now.toLocalDate());
        return voteCrudRepo.save(v, userId, resId);


    }

    @Cacheable("votes")
    public List<VoteTo> getVotesForUser(int userId){
        List<Vote> votes = voteCrudRepo.getVotesByUser(userId);
        return votes
                .stream()
                .map(vote -> {
                    RestaurantTo rTo = restaurantService.getWithMenuInDate(vote.getElected().getId(),vote.getDate());
                    return new VoteTo(vote.getId(),rTo,vote.getDate());
                })
                .collect(Collectors.toList());
    }

    @CacheEvict(value = "votes",allEntries = true)
    public void cacheEict(){}

}
