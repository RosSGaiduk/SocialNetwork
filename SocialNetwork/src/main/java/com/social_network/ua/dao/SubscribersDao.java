package com.social_network.ua.dao;

import com.social_network.ua.entity.subscribersCopy;

/**
 * Created by Rostyslav on 23.01.2017.
 */
public interface SubscribersDao {
    void add(subscribersCopy subscribers);
    void delete(subscribersCopy subscribers);
    boolean checkIfFriends(long id1,long id2);
}
