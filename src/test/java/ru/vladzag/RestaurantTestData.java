package ru.vladzag;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.to.DishTo;
import ru.vladzag.to.RestaurantTo;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.vladzag.model.AbstractBaseEntity.START_SEQ;
import static ru.vladzag.TestUtil.readFromJsonMvcResult;
import static ru.vladzag.TestUtil.readListFromJsonMvcResult;


public class RestaurantTestData {
    public static final int RES1_ID = START_SEQ + 2;
    public static final int RES2_ID = START_SEQ + 3;
    public static final int RES3_ID = START_SEQ + 4;
    public static final int RESWM_ID = START_SEQ + 5;
    public static final int DISH_ID = START_SEQ + 6;
    public static final int DISH2_ID = START_SEQ + 7;
    public static final int RES_SAVED_ID = START_SEQ + 10;
    public static final int DISH_TO_SAVE_ID = START_SEQ + 10;
    public static final LocalDate DATE_2015_05_30 = LocalDate.of(2015, Month.MAY, 30);


    public static final RestaurantTo RES1_TO = new RestaurantTo(RES1_ID,"R1",0);
    public static final RestaurantTo RES1_TO_WITH_VOTE = new RestaurantTo(RES1_ID,"R1",1);
    public static final RestaurantTo RES2_TO = new RestaurantTo(RES2_ID,"R2",0);
    public static final RestaurantTo RES3_TO = new RestaurantTo(RES3_ID,"R3",0);
    public static final RestaurantTo RESWM_TO = new RestaurantTo(RESWM_ID,"RWM",0);
    public static final RestaurantTo RESWM2_TO = new RestaurantTo(RESWM_ID,"RWM",2);



    public static final Restaurant RES1 = new Restaurant(RES1_ID,"R1");
    public static final Restaurant RES1_UPD = new Restaurant(RES1_ID,"R111");
    public static final Restaurant RES2 = new Restaurant(RES2_ID,"R2");
    public static final Restaurant RES3 = new Restaurant(RES3_ID,"R3");
    public static final Restaurant RESWITHMEALS = new Restaurant(RESWM_ID,"RWM");
    public static final Restaurant RES_SAVED = new Restaurant(RES_SAVED_ID,"RTS");
    public static final Dish DISH_2 = new Dish(DISH2_ID,"Cake",200, LocalDate.of(2015, Month.MAY, 29));
    public static final Dish DISH_1 = new Dish(DISH_ID,"Pizza",100, LocalDate.of(2015, Month.MAY, 30));
    public static final Dish DISH_2_UPDATED = new Dish(DISH2_ID,"D2U",250, LocalDate.of(2015, Month.MAY, 29));

    public static final Dish DISH_TO_SAVE = new Dish(DISH_TO_SAVE_ID,"DTS",120,LocalDate.of(2015, Month.MAY, 25));

    public static Restaurant getWithDish(){
        Restaurant r = new Restaurant(RESWM_ID,"RWM");;
        r.getDishes().add(new Dish(DISH_ID,"Pizza",100, LocalDate.of(2015, Month.MAY, 30)));
        r.getDishes().add(new Dish(DISH2_ID,"Cake",200, LocalDate.of(2015, Month.MAY, 29)));
        return r;
    }
    public static RestaurantTo getToWithDishInDate(){
        RestaurantTo r = new RestaurantTo(RESWM_ID,"RWM",2);

        r.getDishes().add(new DishTo(DISH_ID,"Pizza",100));
        return r;
    }

    public static Restaurant getResToSave(){
        return new Restaurant("RTS");
    }
    public static Dish getDishToSave(){
        return new Dish("DTS",120,LocalDate.of(2015, Month.MAY, 25));
    }
    public static final List<Restaurant> RESTAURANTS = List.of(RES1,RES2,RES3,RESWITHMEALS);

    public static ResultMatcher contentJson(Restaurant... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Restaurant.class), List.of(expected));
    }

    public static ResultMatcher contentJson(RestaurantTo... expected) {
        return result -> assertMatchTo(readListFromJsonMvcResult(result, RestaurantTo.class), List.of(expected));
    }

    public static ResultMatcher contentJson(Restaurant expected) {
        return result -> assertMatch(readFromJsonMvcResult(result, Restaurant.class), expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Iterable<Restaurant> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("votes", "dishes").isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Restaurant> actual, Restaurant... expected) {
        assertMatch(actual, List.of(expected));
    }



    public static void assertMatchTo(Iterable<RestaurantTo> actual, Iterable<RestaurantTo> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("votes", "dishes").isEqualTo(expected);
    }

    public static void assertMatch(Restaurant actual, Restaurant expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "votes", "dishes");
    }

    public static void assertMatch(Dish actual, Dish expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }
}
