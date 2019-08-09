package ru.vladzag;

import ru.vladzag.model.Restaurant;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.vladzag.model.AbstractBaseEntity.START_SEQ;

public class RestarauntTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 8;

    public static final Restaurant RESTAURANT1 = new Restaurant("R1");
    public static final Restaurant RESTAURANT2 = new Restaurant("R2");
    public static final Restaurant RESTAURANT3 = new Restaurant("R3");

    //public static final List<Meal> MEALS = List.of(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
}
