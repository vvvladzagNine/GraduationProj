package ru.vladzag.model;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Dish> dishes= new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "elected")
    private Set<Vote> votes = new HashSet<>();

    public Restaurant(String rts) {
        name = rts;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public Restaurant() {

    }
    public Restaurant(Integer id,String name){
        super(id,name);
    }



}
