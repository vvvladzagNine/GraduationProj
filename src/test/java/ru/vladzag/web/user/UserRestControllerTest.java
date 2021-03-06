package ru.vladzag.web.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.vladzag.model.Vote;
import ru.vladzag.service.RestaurantService;
import ru.vladzag.service.VoteService;

import javax.annotation.PostConstruct;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.vladzag.RestaurantTestData.*;
import static ru.vladzag.TestUtil.userHttpBasic;
import static ru.vladzag.UserTestData.USER;
import static ru.vladzag.VoteTestData.*;

@SpringJUnitWebConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-mvc.xml",
        "classpath:spring/spring-db.xml"
})
@Transactional
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class UserRestControllerTest {

    private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

    static {
        CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
        CHARACTER_ENCODING_FILTER.setForceEncoding(true);
    }

    private static final String REST_URL = UserRestController.REST_URL + '/';

    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    RestaurantService service;
    @Autowired
    VoteService vService;

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
                .apply(springSecurity())
                .build();
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL).with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1_TO,RES2_TO,RES3_TO,RESWM_TO));
    }
    @Test
    void getScore() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+"voter?id="+RES1_ID).with(userHttpBasic(USER)));
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+"score").with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RES1_TO_WITH_VOTE,RES2_TO,RES3_TO,RESWM_TO));
    }


    @Test
    void getScoreWithoutVoting() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+"score").with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getRestaurant() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL+RESWM_ID).with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(RESWM_TO));
    }

    @Test
    void createVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+"voter?id="+RES1_ID).with(userHttpBasic(USER)));
        Vote v =vService.get(VOTE1_ID);
        assertThat(v).isEqualToIgnoringGivenFields(VOTE1);
    }

    @Test
    void createTwice() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+"voter?id="+RES1_ID).with(userHttpBasic(USER)));
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+"voter?id="+RES2_ID).with(userHttpBasic(USER)))
                .andExpect(status().isForbidden());

    }

    @Test
        void updateVote() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL+"voter?id="+RES1_ID).with(userHttpBasic(USER)));
        if(LocalTime.now().isAfter(LocalTime.of(11,0)))
            mockMvc.perform(MockMvcRequestBuilders.put(REST_URL+"voter?resId="+RES2_ID+"&voteId="+VOTE1_ID).with(userHttpBasic(USER)))
                    .andExpect(status().isForbidden());
        else{
            mockMvc.perform(MockMvcRequestBuilders.put(REST_URL+"voter?resId="+RES2_ID+"&voteId="+VOTE1_ID).with(userHttpBasic(USER)));
            assertThat(vService.get(VOTE1_ID)).isEqualToIgnoringGivenFields(VOTE1_UPDATED);
        }
    }
}