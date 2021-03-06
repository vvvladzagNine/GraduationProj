package ru.vladzag.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.vladzag.model.User;

@Transactional(readOnly = true)

public interface UserJpaRepo extends JpaRepository<User, Integer> {
    User getByEmail(String email);
}
