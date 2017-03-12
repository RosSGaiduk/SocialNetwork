package com.social_network.ua.dao;

import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.Music;
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
    void addMusicToUser(long idOfUser,long idOfMusic);
    //передаєм масив id-шок тих користувачів, які підписались на даного користувача
    List<User> findAllThatArentFriendsOfUserAndArentSubscribersOfUser(Long[]idsOf);
    List<Long> findAllIdsOfSubscribersOfUser(long id);
    List<User> findAllByInput(String str);
    List<User> findAllUsersOfCommunity(Community community,int limit);
    User selectUser(long id1, long id2);
    List<Music> get3LastMusicOfUser(long userId);
    List<Music> getAllMusicOfUser(long userId);
    User getUserOfMessage(long messageId);
    User getUserToOfMessage(long messageId);
    boolean findUserByEmail(String email);
    List<User> findAll();
}
