package ru.vladzag.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.to.RestaurantTo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vladzag.RestaurantTestData.*;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class RestaurantServiceTest {

    @Autowired
    RestaurantService service;

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
        List<Dish> l1=rest.getDishes();
        List<Dish> l2=rest2.getDishes();
        assertThat(l1).containsExactlyElementsOf(l2);

    }

    @Test
    void getWithMenuInDate() {
        RestaurantTo rest = service.getWithMenuInDate(RESWM_ID,CURRENT_DATE);
        RestaurantTo rest2= getToWithDishInDate();
        assertThat(rest).isEqualToIgnoringGivenFields(rest2,"dishes");
        List<Dish> l1=rest.getDishes();
        List<Dish> l2=rest2.getDishes();
        assertThat(l1).containsExactlyElementsOf(l2);
    }

    @Test
    void getAll() {
        assertThat(service.getAll()).usingElementComparatorIgnoringFields("dishes", "votes").isEqualTo(RESTAURANTS);
    }


    @Test
    void save() {
        Restaurant r = getResToSave();
        service.save(r);
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
        List<Dish> dishes=rest.getDishes();
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



}

