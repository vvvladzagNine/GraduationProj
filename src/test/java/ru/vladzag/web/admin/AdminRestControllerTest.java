package ru.vladzag.web.admin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.vladzag.model.Dish;
import ru.vladzag.model.Restaurant;
import ru.vladzag.service.RestaurantService;
import ru.vladzag.web.json.JsonUtil;

import javax.annotation.PostConstruct;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vladzag.RestaurantTestData.*;
import static ru.vladzag.TestUtil.readFromJson;


@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
//@WebAppConfiguration
//@ExtendWith(SpringExtension.class)
@Transactional
class AdminRestControllerTest {


    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private static final String REST_URL = AdminRestController.REST_URL + '/';

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    RestaurantService service;


    @BeforeEach
    void cacheEvict(){
        service.cacheRestaurantsEvict();
        service.cacheScoreEvict();
    }

    @PostConstruct
    private void postConstruct() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .addFilter(CHARACTER_ENCODING_FILTER)
                .build();
    }

    @Test
    void getAll() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1,RES2,RES3,RESWITHMEALS));
    }

    @Test
    void getAllHistory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "history"))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1_TO,RES2_TO,RES3_TO,RESWM2_TO));
    }

    @Test
    void get() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + RESWM_ID+ "?date=2015-05-30"))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getToWithDishInDate()));
    }



    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL+RES1_ID))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(service.getAll(),RES2,RES3,RESWITHMEALS);

    }

    @Test
    void update() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL+RES1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(RES1_UPD)))
                .andExpect(status().isNoContent());
        assertMatch(service.getAll(),RES1_UPD,RES2,RES3,RESWITHMEALS);
    }
    @Test
    void createWithLocation() throws Exception {
        Restaurant created = getResToSave();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)));

        Restaurant returned = readFromJson(action, Restaurant.class);
        created.setId(returned.getId());
        assertMatch(returned, created);
        //In spite of @Transactional sequence has not been restarted that's why saved entity id is 100010 or 100011
        assertThat(service.getAll()).usingElementComparatorIgnoringFields("id","votes","dishes").isEqualTo(List.of(RES1,RES2,RES3,RESWITHMEALS,RES_SAVED));
    }
    @Test
    void createDishWithLocation() throws Exception {
        Dish created = getDishToSave();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL + RESWM_ID + "/dishes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(created)));

        Dish returned = readFromJson(action,Dish.class);
        created.setId(returned.getId());
        assertMatch(returned, created);


        //Restaurant r = service.getWithMenu(RESWM_ID);
        Dish d = service.getDish(DISH_TO_SAVE_ID);
        assertThat(d).isEqualToIgnoringGivenFields(DISH_TO_SAVE,"restaurant");
        // assertThat(r.getDishes()).containsExactlyInAnyOrder(DISH_TO_SAVE,DISH_1,DISH_2);
    }
    @Test
    void updateDish() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL+RESWM_ID+"/dishes/"+DISH2_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(DISH_2_UPDATED)));
        assertThat(service.getDish(DISH2_ID)).isEqualToIgnoringGivenFields(DISH_2_UPDATED,"restaurant");

    }
    @Test
    void deleteDish() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL+RES1_ID))
                .andExpect(status().isNoContent())
                .andDo(print());

        assertMatch(service.getAll(),RES2,RES3,RESWITHMEALS);
    }
}