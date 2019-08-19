package ru.vladzag.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.vladzag.model.Vote;
import ru.vladzag.util.exception.VoteExpiredException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.vladzag.VoteTestData.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ExtendWith(SpringExtension.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
class VoteServiceTest {

    @Autowired
    VoteService service;

    @Test
    void createVote() throws VoteExpiredException {
        Vote v = new Vote();
        v.setDate(LocalDate.now());
        service.createVote(v,100000,100002);
        assertThat(service.get(100010)).isEqualToIgnoringGivenFields(VOTE1);
    }

    @Test
    void createTwice() throws VoteExpiredException {
        Vote v = new Vote();
        v.setDate(LocalDate.now());
        service.createVote(v,100000,100002);
        assertThat(service.get(100010)).isEqualToIgnoringGivenFields(VOTE1);
        Vote v2 = new Vote();
        v.setDate(LocalDate.now());
        assertThrows(VoteExpiredException.class,()->service.createVote(v,100000,100003));
    }

    @Test
    void updateVote() throws VoteExpiredException {
        Vote v = new Vote();
        v.setDate(LocalDate.now());
        service.createVote(v,100000,100002);
        try {
            service.updateVote(service.get(100010),100000,100003);

            if(LocalTime.now().isAfter(LocalTime.of(11,0))){
                throw new VoteExpiredException("User is available to change his mind after 11am !");
            }
            assertThat(service.get(100010)).isEqualToIgnoringGivenFields(VOTE1_UPDATED);

        }
        catch (VoteExpiredException e){
            if(LocalTime.now().isBefore(LocalTime.of(11,0))){
                throw new VoteExpiredException("User is not available to change his mind before 11am !");
            }
            assertThat(service.get(100010)).isEqualToIgnoringGivenFields(VOTE1);

        }


    }

}