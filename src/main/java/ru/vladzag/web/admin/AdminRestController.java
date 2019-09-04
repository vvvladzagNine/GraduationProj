package ru.vladzag.web.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.service.RestaurantService;
import ru.vladzag.to.RestaurantTo;
import ru.vladzag.util.DateTimeUtil;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.vladzag.util.ValidationUtil.assureIdConsistent;
import static ru.vladzag.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestController {

    private final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    RestaurantService service;

    public static final String REST_URL = "/rest/admin/restaurants";

    @GetMapping
    public List<Restaurant> getAll() {
        log.info("Admin getAll");
        return service.getAll();
    }

    @GetMapping("/history")
    public List<RestaurantTo> getAllHistory(@RequestParam(required = false) String date) {
        //log.info("getAll");
        if(date==null) {
            log.info("Admin getHistory");
            return service.getAllToWithCountOfVotes();
        }
        else {
            log.info("Admin getHistory in date:{}",date);
            return service.getAllWithVotes( DateTimeUtil.parseLocalDate(date));
        }
    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id, @RequestParam(required = false) String date) {
        if(date==null) {
            log.info("Admin getRestaurant(id: {}) with all dishes and votes",id);
            return service.getWithMenuAndVotesInDate(id, LocalDate.now());
        }
        else {
            log.info("Admin getRestaurant(id: {}) in date: {}",id,date);
            return service.getWithMenuAndVotesInDate(id, DateTimeUtil.parseLocalDate(date));
        }
    }

    @GetMapping("/dishes/{id}")
    public Dish get(@PathVariable int id) {
        log.info("Admin getDish with id: {}", id);
        return service.getDish(id);
    }




    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("Admin deleteRestaurant with id: {}",id);
        service.delete(id);
    }


    @PutMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestParam String restaurantName, @PathVariable int id) {
        log.info("Admin updateRestaurant with id: {}",id);

        service.update(new Restaurant(id,restaurantName));
    }



    @PostMapping
    public ResponseEntity<Restaurant> createWithLocation(
            @RequestParam String restaurantName) {
        //checkNew(res);
        Restaurant created = service.create(new Restaurant(restaurantName));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        log.info("Admin createRestaurant with id: {}",created.getId());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/{resId}/dishes",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(
            @RequestBody Dish d,
            @PathVariable int resId) {
        checkNew(d);
        Dish created = service.createDish(d,resId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/dishes/{id}")
                .buildAndExpand(created.getId()).toUri();
        log.info("Admin createDish with id: {}",created.getId());
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{resId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@RequestBody Dish dish,
                           @PathVariable int id,
                           @PathVariable int resId) {
        assureIdConsistent(dish, id);
        log.info("Admin updateDish with id: {}",dish.getId());
        service.updateDish(dish,resId);
    }

    @DeleteMapping("/{resId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int id,
                       @PathVariable int resId) {
        log.info("Admin deleteDish with id: {}",id);
        service.deleteDish(id);
    }



}