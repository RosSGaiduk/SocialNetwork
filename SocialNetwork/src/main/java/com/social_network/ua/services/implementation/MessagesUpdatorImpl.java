package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.MessagesUpdatorDao;
import com.social_network.ua.entity.MessagesUpdator;
import com.social_network.ua.services.MessagesUpdatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Rostyslav on 11.12.2016.
 */
@Service
public class MessagesUpdatorImpl implements  MessagesUpdatorService {

    @Autowired
    private MessagesUpdatorDao messagesUpdatorDao;

    @Override
    public void add(MessagesUpdator messagesUpdator) {
        messagesUpdatorDao.add(messagesUpdator);
    }

    @Override
    public void delete(long id) {
        messagesUpdatorDao.delete(findOne(id));
    }

    @Override
    public void edit(MessagesUpdator messagesUpdator) {
        messagesUpdatorDao.edit(messagesUpdator);
    }

    @Override
    public boolean findMessageBetweenUsers(long id1, long id2) {
        return messagesUpdatorDao.findMessageBetweenUsers(id1,id2);
    }

    @Override
    public MessagesUpdator findOne(long id) {
        return messagesUpdatorDao.findOne(id);
    }

    @Override
    public MessagesUpdator findOneBy2Ids(long id1, long id2) {
        return messagesUpdatorDao.findOneBy2Ids(id1,id2);
    }

    @Override
    public void deleteWhereUserFromLikeId1AndUserToLikeId2(long id1, long id2) {
        messagesUpdatorDao.deleteWhereUserFromLikeId1AndUserToLikeId2(id1,id2);
    }

    @Override
    public int findCountByIdUserFromAndIdUserTo(long idUserFrom, long idUserTo) {
        return messagesUpdatorDao.findCountByIdUserFromAndIdUserTo(idUserFrom,idUserTo);
    }

    @Override
    public List<MessagesUpdator> findAll() {
        return messagesUpdatorDao.findAll();
    }
}
