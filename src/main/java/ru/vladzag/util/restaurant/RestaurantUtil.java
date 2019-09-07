package ru.vladzag.util.restaurant;

import ru.vladzag.model.Restaurant;
import ru.vladzag.to.DishTo;
import ru.vladzag.to.RestaurantTo;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class RestaurantUtil {
    public static List<Restaurant> getInDate(List<Restaurant> restaurants, LocalDate date) {
        return null;
    }

    public static RestaurantTo getWithFilteredDishes(Restaurant r,LocalDate date){
        RestaurantTo rTo = new RestaurantTo(r.getId(),r.getName());
        rTo.setDishes(r.getDishes()
                .stream()
                .filter(dish -> dish.getDate().equals(date))
                .map(dish -> new DishTo(dish.getId(),dish.getName(),dish.getPrice()))
                .collect(Collectors.toList()));

        return rTo;
    }

    public static RestaurantTo getWithFilteredDishesAndCountOfVotes(Restaurant r,LocalDate date){
        RestaurantTo rTo = new RestaurantTo(r.getId(),r.getName());
        rTo.setDishes(r.getDishes()
                .stream()
                .filter(dish -> dish.getDate().equals(date))
                .map(dish -> new DishTo(dish.getId(),dish.getName(),dish.getPrice()))
                .collect(Collectors.toList()));
        rTo.setCountOfVotes((int)r.getVotes().stream().filter(vote -> vote.getDate().equals(date)).count());

        return rTo;
    }

    public static RestaurantTo getWithFilteredCountOfVotes(Restaurant r,LocalDate date){
        RestaurantTo rTo = new RestaurantTo(r.getId(),r.getName());
        rTo.setCountOfVotes((int)r.getVotes().stream().filter(vote -> vote.getDate().equals(date)).count());
        return rTo;
    }

    public static List<RestaurantTo> getAllWithFilteredCountOfVotes(List<Restaurant> list,LocalDate date){
        return list.stream()
                .map(r ->{
                    RestaurantTo rTo = new RestaurantTo(r.getId(),r.getName());
                    rTo.setCountOfVotes((int)r.getVotes().stream().filter(vote -> vote.getDate().equals(date)).count());
                    return rTo; })
                .sorted(Comparator.comparingInt(RestaurantTo::getCountOfVotes).reversed())
                .collect(Collectors.toList());


    }

    public static List<RestaurantTo> getAllWithFilteredCountOfVotesInAllTime(List<Restaurant> list){
        return list.stream()
                .map(r ->{
                    RestaurantTo rTo = new RestaurantTo(r.getId(),r.getName());
                    rTo.setCountOfVotes(r.getVotes().size());
                    return rTo; })
                .collect(Collectors.toList());


    }
}
