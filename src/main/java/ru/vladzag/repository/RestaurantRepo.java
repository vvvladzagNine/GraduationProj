package ru.vladzag.repository;

import ru.vladzag.model.Restaurant;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantRepo {


    Restaurant save(Restaurant r);

    boolean delete(int id);

    Restaurant get(int id);

    List<Restaurant> getAll();

    // ORDERED dateTime desc
    Restaurant getWithMenu(int id);




}
