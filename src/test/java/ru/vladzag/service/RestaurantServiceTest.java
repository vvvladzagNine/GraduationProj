package ru.vladzag.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.vladzag.model.Restaurant;

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
    }

    @Test
    void save() {
    }

    @Test
    void getAllInDate() {
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

    @Test
    void delete() {
    }
}