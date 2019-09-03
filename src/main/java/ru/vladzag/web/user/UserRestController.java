package ru.vladzag.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.vladzag.model.Restaurant;
import ru.vladzag.model.Vote;
import ru.vladzag.service.RestaurantService;
import ru.vladzag.service.VoteService;
import ru.vladzag.to.RestaurantTo;
import ru.vladzag.to.VoteTo;
import ru.vladzag.util.exception.ScoreAccessException;
import ru.vladzag.web.SecurityUtil;
import ru.vladzag.web.admin.AdminRestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    public static final String REST_URL = "/rest/user/restaurants";

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    VoteService vService;

    @Autowired
    RestaurantService rService;

    @GetMapping
    public List<Restaurant> getAll() {
        return rService.getAll();
    }

    @GetMapping("/score")
    public List<RestaurantTo> getScore() {
        log.info("User getScore");
        return rService.getScoreForUser(SecurityUtil.authUserId());
    }

    @GetMapping("/history")
    public List<VoteTo> getMyVotes(){
        log.info("User getHistory");
        return vService.getVotesForUser(SecurityUtil.authUserId());
    }

    @GetMapping("/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id) {
        log.info("User getRestaurant with id: {}",id);
        return rService.getWithMenuInDate(id, LocalDate.now());
    }

    @PostMapping("/{id}")
    public Vote createVote(@PathVariable int id) throws Exception{
        log.info("User vote for restaurant with id: {}",id);
        return vService.createVote(SecurityUtil.authUserId(),id);
    }

    @PutMapping("/{id}/{voteId}")
    public void updateVote(@PathVariable(name = "id") int resId,@PathVariable int voteId) throws Exception{
        log.info("User updateVote(id: {}) for restaurant with id: {}",voteId,resId);
        vService.updateVote(voteId, SecurityUtil.authUserId(),resId);
    }

    }

