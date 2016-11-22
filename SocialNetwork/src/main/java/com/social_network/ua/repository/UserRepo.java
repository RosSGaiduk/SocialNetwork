package com.social_network.ua.repository;

import com.social_network.ua.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by Rostyslav on 21.11.2016.
 */
public interface UserRepo extends JpaRepository<User,Integer> {
    @Query("SELECT u FROM User u WHERE u.email LIKE :login")
    User findByLogin(@Param("login") String login);
}