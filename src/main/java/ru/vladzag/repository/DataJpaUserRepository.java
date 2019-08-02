package ru.vladzag.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.vladzag.model.User;


import java.util.List;

@Repository
public class DataJpaUserRepository {
    private static final Sort SORT_NAME_EMAIL = new Sort(Sort.Direction.ASC, "name", "email");

    @Autowired
    private CrudUserRepository crudRepository;


    public User save(User user) {
        return crudRepository.save(user);
    }

/*
    public boolean delete(int id) {
        return crudRepository.delete(id) != 0;
    }

 */


    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }


    //public User getByEmail(String email) {
        //return crudRepository.getByEmail(email);
    //}


    public List<User> getAll() {
        return crudRepository.findAll(SORT_NAME_EMAIL);
    }
}
