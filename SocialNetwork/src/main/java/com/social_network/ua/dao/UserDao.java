package com.social_network.ua.dao;

import com.social_network.ua.entity.User;

import java.util.List;

/**
 * Created by Rostyslav on 21.11.2016.
 */
public interface UserDao {
    void add(User user);
    void delete(User user);
    void edit(User user);
    User findOne(long id);
    void addFriendToUser(long idOfUser,long idOfFriend);
    User selectUser(long id1, long id2);
    List<User> findAll();
}
