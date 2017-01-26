package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.SubscribersDao;
import com.social_network.ua.entity.subscribersCopy;
import com.social_network.ua.services.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rostyslav on 23.01.2017.
 */
@Service
public class SubscribersServiceImpl implements SubscriberService{
    @Autowired
    private SubscribersDao subscribersDao;

    @Override
    public void add(subscribersCopy subscribers) {
        subscribersDao.add(subscribers);
    }


    @Override
    public void delete(subscribersCopy subscribers) {
        subscribersDao.delete(subscribers);
    }

    @Override
    public boolean checkIfFriend(long id1, long id2) {
        return subscribersDao.checkIfFriends(id1,id2);
    }
}
