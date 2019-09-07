package ru.vladzag.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.vladzag.to.VoteTo;
import ru.vladzag.util.exception.VoteException;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.vladzag.RestaurantTestData.RES1_ID;
import static ru.vladzag.RestaurantTestData.RES2_ID;
import static ru.vladzag.UserTestData.USER_ID;
import static ru.vladzag.VoteTestData.*;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class VoteServiceTest {

    @Autowired
    VoteService service;

    @BeforeEach
    void cacheEvict(){
        service.cacheEvict();
    }


    @Test
    void createVote() throws VoteException {
        service.createVote(USER_ID,RES1_ID);
        assertThat(service.get(VOTE1_ID)).isEqualToIgnoringGivenFields(VOTE1);
    }

    @Test
    void createTwice() throws VoteException {

        service.createVote(USER_ID,RES1_ID);
        assertThat(service.get(VOTE1_ID)).isEqualToIgnoringGivenFields(VOTE1);

        assertThrows(VoteException.class,()->service.createVote(USER_ID,RES2_ID));
    }

    @Test
    void updateVote() throws VoteException {

        service.createVote(USER_ID,RES1_ID);
        try {
            service.updateVote(VOTE1_ID,USER_ID,RES2_ID);

            if(LocalTime.now().isAfter(LocalTime.of(11,0))){
                throw new VoteException("User is not available to change his mind after 11am !");
            }
            assertThat(service.get(VOTE1_ID)).isEqualToIgnoringGivenFields(VOTE1_UPDATED);

        }
        catch (VoteException e){
            if(LocalTime.now().isBefore(LocalTime.of(11,0))){
                throw new VoteException("User is available to change his mind before 11am !");
            }
            assertThat(service.get(VOTE1_ID)).isEqualToIgnoringGivenFields(VOTE1);

        }

    }

    @Test
    void getVotesForUser() {
        List<VoteTo> votes = service.getVotesForUser(USER_ID);
        assertThat(votes).usingElementComparatorIgnoringFields().isEqualTo(List.of(VOTE_EXISTED_TO));

        assertThat(votes.get(0).getElected().getDishes()).usingElementComparatorIgnoringFields().isEqualTo(VOTE_EXISTED_TO.getElected().getDishes());



    }
}