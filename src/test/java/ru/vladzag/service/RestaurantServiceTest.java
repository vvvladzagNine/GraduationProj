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

    }

    @Test
    void delete() {
    }

    @Test
    void updateDish() {
    }

    @Test
    void createDish() {
    }

    @Test
    void deleteDish() {
    }



}

