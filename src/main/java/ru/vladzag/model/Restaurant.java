package ru.vladzag.model;


import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {


    //TODO проверку на одинаковые имена ресторанов
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
