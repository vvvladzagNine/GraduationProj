package ru.vladzag;

import ru.vladzag.model.Restaurant;
import ru.vladzag.model.Vote;


import java.time.LocalDate;

import static ru.vladzag.RestaurantTestData.*;
import static ru.vladzag.UserTestData.*;

import static ru.vladzag.model.AbstractBaseEntity.START_SEQ;

public class VoteTestData {
    public static final int VOTE1_ID = START_SEQ + 10;

    public static final Vote VOTE1 = new Vote(VOTE1_ID,USER,RES1, LocalDate.now());
    public static final Vote VOTE1_UPDATED = new Vote(VOTE1_ID,USER,RES2, LocalDate.now());


}
