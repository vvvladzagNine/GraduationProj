package ru.vladzag;

import ru.vladzag.model.Vote;
import ru.vladzag.to.VoteTo;


import java.time.LocalDate;

import static ru.vladzag.RestaurantTestData.*;
import static ru.vladzag.UserTestData.*;

import static ru.vladzag.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final int VOTE1_ID = START_SEQ + 10;
    public static final int VOTE_EXISTED_ID = START_SEQ + 8;

    public static final Vote VOTE1 = new Vote(VOTE1_ID,USER,RES1, LocalDate.now());
    public static final Vote VOTE1_UPDATED = new Vote(VOTE1_ID,USER,RES2, LocalDate.now());

    public static final VoteTo VOTE_EXISTED_TO = new VoteTo(VOTE_EXISTED_ID,getToWithDishInDate(),DATE_2015_05_30);


}
