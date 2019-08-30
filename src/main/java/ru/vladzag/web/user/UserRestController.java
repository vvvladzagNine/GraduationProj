package ru.vladzag.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.vladzag.model.Restaurant;
import ru.vladzag.model.Vote;
import ru.vladzag.service.RestaurantService;
import ru.vladzag.service.VoteService;
import ru.vladzag.to.RestaurantTo;
import ru.vladzag.util.exception.ScoreAccessException;
import ru.vladzag.web.SecurityUtil;
import ru.vladzag.web.admin.AdminRestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    public static final String REST_URL = "/rest/user/restaurants";

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
        return rService.getScoreForUser(SecurityUtil.authUserId());
    }

    @GetMapping("/{id}")
    public RestaurantTo getRestaurant(@PathVariable int id) {
        return rService.getWithMenuInDate(id, LocalDate.now());
    }

    @PostMapping("/{id}")
    public Vote createVote(@PathVariable int id) throws Exception{
        return vService.createVote(SecurityUtil.authUserId(),id);
    }

    @PutMapping("/{id}/{voteId}")
    public void updateVote(@PathVariable(name = "id") int resId,@PathVariable int voteId) throws Exception{
        vService.updateVote(voteId, SecurityUtil.authUserId(),resId);
    }

    }

