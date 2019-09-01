package ru.vladzag.web.admin;

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



    @Autowired
    RestaurantService service;

    public static final String REST_URL = "/rest/admin/restaurants";

    @GetMapping
    public List<Restaurant> getAll() {
        //log.info("getAll");
        return service.getAll();
    }

    @GetMapping("/history")
    public List<RestaurantTo> getAllHistory(@RequestParam(required = false) String date) {
        //log.info("getAll");
        if(date==null) {
            return service.getAllToWithCountOfVotes();
        }
        else {
            return service.getAllWithVotes( DateTimeUtil.parseLocalDate(date));
        }
    }

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id, @RequestParam(required = false) String date) {
        if(date==null) {
            return service.getWithMenuAndVotesInDate(id, LocalDate.now());
        }
        else
        return service.getWithMenuAndVotesInDate(id, DateTimeUtil.parseLocalDate(date));
    }

    @GetMapping("/dishes/{id}")
    public Dish get(@PathVariable int id) {
        return service.getDish(id);
    }




    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Restaurant res, @PathVariable int id) {
        assureIdConsistent(res, id);
        service.update(res);
    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@RequestBody Restaurant res) {
        // log.info("create {}", user);
        checkNew(res);
        Restaurant created = service.create(res);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PostMapping(value = "/{resId}/dishes",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createDish(
            @RequestBody Dish d,
            @PathVariable int resId) {
        // log.info("create {}", user);
        checkNew(d);
        Dish created = service.createDish(d,resId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/dishes/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{resId}/dishes/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateDish(@RequestBody Dish dish,
                           @PathVariable int id,
                           @PathVariable int resId) {
        assureIdConsistent(dish, id);
        service.updateDish(dish,id);
    }

    @DeleteMapping("/{resId}/dishes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable int id,
                       @PathVariable int resId) {
        service.deleteDish(id);
    }



}