package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.MessageDao;
import com.social_network.ua.entity.Message;
import com.social_network.ua.entity.User;
import com.social_network.ua.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 21.11.2016.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageDao messageDao;

    @Override
    public void add(Message message) {
        messageDao.add(message);
    }

    @Override
    public void add(User userFrom, User userTo, String text, Date dateOfMessage) {
        Message message = new Message(userFrom,userTo,text,dateOfMessage);
        messageDao.add(message);
    }

    @Override
    public void edit(long id) {
        Message message = findOne(id);
        messageDao.edit(message);
    }

    @Override
    public void delete(long id) {
        messageDao.delete(findOne(id));
    }

    @Override
    public Message findOne(long id) {
        return messageDao.findOne(id);
    }

    @Override
    public long findAllLastBy2ids(long id1, long id2) {
        return messageDao.findAllLastBy2ids(id1,id2);
    }

    @Override
    public long findAllByIds(long id1,long id2) {
        return messageDao.findAllByIds(id1,id2);
    }

    @Override
    public List<Message> findAllByIdsAndMaxId(long id1,long id2, long maxId) {
        return messageDao.findAllByIdsAndMaxId(id1,id2,maxId);
    }

    @Override
    public long findLastIdOfMessageBetweenUsers(long id1, long id2) {
        return messageDao.findLastIdOfMessageBetweenUsers(id1,id2);
    }

    @Override
    public List<Message> findAll() {
        return messageDao.findAll();
    }
}
