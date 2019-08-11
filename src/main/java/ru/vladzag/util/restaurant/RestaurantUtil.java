package ru.vladzag.util.restaurant;

import ru.vladzag.model.Restaurant;
import ru.vladzag.to.RestaurantTo;

import java.time.LocalDate;
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
                .collect(Collectors.toList()));

        return rTo;
    }
}
