package ru.vladzag;

import ru.vladzag.model.Restaurant;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.vladzag.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static final int RES1_ID = START_SEQ + 2;
    public static final int RES2_ID = START_SEQ + 3;
    public static final int RES3_ID = START_SEQ + 4;
    public static final int ADMIN_MEAL_ID = START_SEQ + 8;



    public static final Restaurant RES1 = new Restaurant(RES1_ID,"R1");
    public static final Restaurant RES2 = new Restaurant(RES2_ID,"R2");
    public static final Restaurant RES3 = new Restaurant(RES3_ID,"R3");

    public static final List<Restaurant> RESTAURANTS = List.of(RES1,RES2,RES3);
}
