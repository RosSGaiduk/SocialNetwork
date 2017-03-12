package com.social_network.ua.services;

import com.social_network.ua.entity.Message;
import com.social_network.ua.entity.User;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Rostyslav on 21.11.2016.
 */
public interface MessageService {
    void add(Message message);
    void add(User userFrom, User userTo, String text, Date dateOfMessage);
    void edit(long id);
    void delete(long id);
    Message findOne(long id);
    long findAllLastBy2ids(long id1,long id2);
    //count - кількість - скільки останніх елементів треба знайти
    long findAllByIds(long id1,long id2);
    List<Message> findAllByIdsAndMaxId(long id1, long id2, long maxId);
    long findLastIdOfMessageBetweenUsers(long id1, long id2);
    List<Message> findAllByIdsAndMinId(long id1, long id2, long minId);
    Set<Message> getAllChatsWithAuthUser(User user);
    List<Message> findAllByUser(User user);
    void updateMessagesImageOfUser(User user,String imageSrc);
    List<Message> findAll();
}
