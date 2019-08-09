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
    @Query("SELECT m from Vote m WHERE  m.date=:date")
    List<Vote> getInDate(@Param("date") LocalDate date);


}
