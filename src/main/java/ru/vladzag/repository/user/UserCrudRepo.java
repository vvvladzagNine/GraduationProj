package ru.vladzag.repository.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.vladzag.model.User;


import java.util.List;

@Repository
public class UserCrudRepo {

    @Autowired
    private UserJpaRepo crudRepository;


    public User save(User user) {
        return crudRepository.save(user);
    }

    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    public List<User> getAll() {
        return crudRepository.findAll();
    }
}
