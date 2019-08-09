package ru.vladzag.model;

import org.hibernate.annotations.Columns;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "restaurants")
public class Restaurant extends AbstractNamedEntity {


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Dish> dishes;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "elected")
    private List<Vote> votes;


    public List<Dish> getDishes() {
        return dishes;
    }

    public Restaurant() {

    }
    public Restaurant(Integer id,String name){
        super(id,name);
    }



}
