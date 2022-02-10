package com.example.untitled.dao;

import com.example.untitled.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,Long> {

    @Query(value = "select * from user where login=:login")
    User findByLogin(String login);
    @Query(value = "select * from user where login=:login and passwordHash=:passwordHash")
    User getUserByLoginAndHash(String login, String passwordHash);

}
