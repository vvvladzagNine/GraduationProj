package ru.vladzag.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.vladzag.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepo extends JpaRepository<Vote,Integer> {

    Vote getById(int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT v from Vote v WHERE  v.date=:date")
    List<Vote> getInDate(@Param("date") LocalDate date);

    //@SuppressWarnings("JpaQlInspection")
    @Query("SELECT v from Vote v WHERE  v.date=:date AND v.voter.id=:userId")
    Vote getInDateByUser(@Param("date") LocalDate date,@Param("userId") int userId);

    @Query("SELECT v from Vote v WHERE v.voter.id=:userId")
    List<Vote> getVotesByUser(@Param("userId") int userId);


}
