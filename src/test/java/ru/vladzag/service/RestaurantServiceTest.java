package ru.vladzag.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.to.DishTo;
import ru.vladzag.to.RestaurantTo;
import ru.vladzag.util.exception.ScoreAccessException;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.vladzag.RestaurantTestData.*;
import static ru.vladzag.UserTestData.*;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class RestaurantServiceTest {

    @Autowired
    RestaurantService service;

    @Autowired
    VoteService vService;




    @BeforeEach
    void cacheEvict(){
        service.cacheRestaurantsEvict();
        service.cacheScoreEvict();
    }



    @Test
    void get() {
        Restaurant rest = service.get(RES2_ID);
        assertThat(rest).isEqualToIgnoringGivenFields(RES2, "dishes", "votes");
    }

    @Test
    void getWithMenu() {
        Restaurant rest = service.getWithMenu(RESWM_ID);
        Restaurant rest2=getWithDish();
        assertThat(rest).isEqualToIgnoringGivenFields(rest2, "votes","dishes");
        Set<Dish> l1=rest.getDishes();
        Set<Dish> l2=rest2.getDishes();
        assertThat(l1).containsExactlyElementsOf(l2);

    }

    @Test
    void getWithMenuInDate() {
        RestaurantTo rest = service.getWithMenuInDate(RESWM_ID, DATE_2015_05_30);
        RestaurantTo rest2= getToWithDishInDate();
        assertThat(rest).isEqualToIgnoringGivenFields(rest2,"dishes","countOfVotes");
        List<DishTo> l1=rest.getDishes();
        List<DishTo> l2=rest2.getDishes();
        assertThat(l1).containsExactlyElementsOf(l2);
    }


    @Test
    void getWithMenuInDateAndVotes() {
        RestaurantTo rest = service.getWithMenuAndVotesInDate(RESWM_ID, DATE_2015_05_30);
        RestaurantTo rest2= getToWithDishInDate();
        assertThat(rest).isEqualToIgnoringGivenFields(rest2,"dishes");
        List<DishTo> l1=rest.getDishes();
        List<DishTo> l2=rest2.getDishes();
        assertThat(l1).containsExactlyElementsOf(l2);
    }

    @Test
    void getAll() {
        assertThat(service.getAll()).usingElementComparatorIgnoringFields("dishes", "votes").isEqualTo(RESTAURANTS);

    }


    @Test
    void save() {

        service.create(getResToSave());
        assertThat(service.getAll()).containsExactlyInAnyOrder(RES1,RES2,RES3,RESWITHMEALS,RES_SAVED);
    }

    @Test
    void delete() {
        service.delete(RESWM_ID);
        assertThat(service.getAll()).containsExactlyInAnyOrder(RES1,RES2,RES3);
    }

    @Test
    void createDish() {
        service.createDish(getDishToSave(),RES1_ID);
        Restaurant rest = service.getWithMenu(RES1_ID);
        Set<Dish> dishes=rest.getDishes();
        assertThat(dishes).containsExactlyInAnyOrder(DISH_TO_SAVE);

    }

    @Test
    void deleteDish() {
        service.deleteDish(DISH_ID);
        Restaurant rest = service.getWithMenu(RESWM_ID);
        assertThat(rest.getDishes()).containsExactlyInAnyOrder(DISH_2);
    }

    @Test
    void updateDish() {
        service.updateDish(DISH_2_UPDATED,RESWM_ID);
        Restaurant r = service.getWithMenu(RESWM_ID);
        assertThat(r.getDishes()).containsExactlyInAnyOrder(DISH_2_UPDATED,DISH_1);

    }


    @Test
    void getWithVotes() {
        RestaurantTo rTo =service.getWithVotes(RESWM_ID, DATE_2015_05_30);
        assertThat(rTo).isEqualToIgnoringGivenFields(getToWithDishInDate(),"dishes");


    }

    @Test
    void getAllWithVotes() {
        List<RestaurantTo> listTo = service.getAllWithVotes(DATE_2015_05_30);
        assertThat(listTo).usingElementComparatorIgnoringFields("dishes").isEqualTo(List.of(RES1_TO,RES2_TO,RES3_TO,getToWithDishInDate()));

    }

    @Test
    void getScoreForUser() throws Exception {
        vService.createVote(USER_ID,RES1_ID);
        List<RestaurantTo> listTo = service.getScoreForUser(USER_ID);
        if(LocalTime.now().isAfter(LocalTime.of(11,0)))
            assertThat(listTo).usingElementComparatorIgnoringFields("dishes").isEqualTo(List.of(RES1_TO_WITH_VOTE,RES2_TO,RES3_TO,RESWM_TO));
        else
            assertThat(listTo).usingElementComparatorIgnoringFields("dishes").isEqualTo(null);


    }
    @Test
    void getScoreForUserWithoutVoting() {


        assertThrows(ScoreAccessException.class,()->service.getScoreForUser(USER_ID));
        //assertThat(listTo).usingElementComparatorIgnoringFields("dishes").isEqualTo(null);



    }
}

