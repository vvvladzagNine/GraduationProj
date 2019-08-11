package ru.vladzag.to;

import ru.vladzag.model.Dish;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RestaurantTo {

    private Integer id;

    private String name;



    //private int countOfVotes;

    private List<Dish> dishes = new ArrayList<>();

    public RestaurantTo(Integer id, String name) {
        this.id = id;
        this.name = name;

    }

    public RestaurantTo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo that = (RestaurantTo) o;
        return name == that.name &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
