package ru.vladzag.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import ru.vladzag.util.DateTimeUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User voter;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "res_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant elected;

    @Column(name = "date", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDate date = LocalDate.now();

    public Vote() {
    }

    public Vote(Integer id,User voter,Restaurant elected, LocalDate date) {
        this.id=id;
        this.voter = voter;
        this.elected = elected;
        this.date = date;
    }


    public User getVoter() {
        return voter;
    }

    public void setVoter(User voter) {
        this.voter = voter;
    }

    public Restaurant getElected() {
        return elected;
    }

    public void setElected(Restaurant elected) {
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
        return "Vote{" +
                "voter=" + voter.getId() +
                ", elected=" + elected.getId() +
                ", date=" + date +
                '}';
    }
}
