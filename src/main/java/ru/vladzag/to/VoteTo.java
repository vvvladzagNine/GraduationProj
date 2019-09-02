package ru.vladzag.to;

import org.springframework.format.annotation.DateTimeFormat;
import ru.vladzag.model.Restaurant;
import ru.vladzag.model.Vote;
import ru.vladzag.util.DateTimeUtil;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public class VoteTo {


    private RestaurantTo elected;

    private Integer id;

    private LocalDate date;



    public VoteTo(int id,RestaurantTo elected, LocalDate date) {
        this.elected = elected;
        this.date = date;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public VoteTo() {
    }

    public RestaurantTo getElected() {
        return elected;
    }

    public void setElected(RestaurantTo elected) {
        this.elected = elected;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "VoteTo{"+
                "id: "+ id +
                ", restaurant: "+ elected.getName() +
                ", date: " + date +
                "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,date,elected.getName());
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if(obj == null || obj.getClass()!=getClass())return false;
        Vote that = (Vote)obj;
        return that.getDate().equals(date)
                && that.getElected().getName().equals(elected.getName())
                && Objects.equals(id,that.getId());

    }
}
