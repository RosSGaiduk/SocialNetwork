package com.social_network.ua.services;

import com.social_network.ua.entity.User;
import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 21.11.2016.
 */
public interface UserService {
    void add(String firstName, String lastName, Date birthDate, String email, String password);
    void add(User user);
    void delete(long id);
    void edit(long id,String firstName, String lastName, Date birthDate, String email,String password);
    void edit(User user);
    User findOne(long id);
    void addFriendToUser(long id1,long id2);
    void addMusicToUser(long idOfUser,long idOfMusic);
    List<User> findAllThatArentFriendsOfUserAndArentSubscribersOfUser(Long[]idsOf);
    List<Long> findAllIdsOfSubscribersOfUser(long id);
    List<User> findAllByInput(String str);
    User selectUser(long id1,long id2);
    List<User> findAll();
}
