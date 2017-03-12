package com.social_network.ua.dao;

import com.social_network.ua.entity.Message;
import com.social_network.ua.entity.User;

import java.util.List;
import java.util.Set;

/**
 * Created by Rostyslav on 21.11.2016.
 */
public interface MessageDao {
    void add(Message message);
    void edit(Message message);
    void delete(Message message);
    Message findOne(long id);
    long findAllLastBy2ids(long id1,long id2);
    long findAllByIds(long id1,long id2);
    long findLastIdOfMessageBetweenUsers(long id1,long id2);
    List<Message> findAllByIdsAndMaxId(long id1, long id2,long maxId);
    List<Message> findAllByUser(User user);
    List<Message> findAllByIdsAndMinId(long id1, long id2,long minId);
    void updateMessagesImageOfUser(User user,String imageSrc);
    Set<Message> getAllChatsWithAuthUser(User user);
    List<Message> findAll();
}
