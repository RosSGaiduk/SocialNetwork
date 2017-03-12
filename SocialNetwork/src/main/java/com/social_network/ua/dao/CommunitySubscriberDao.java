package com.social_network.ua.dao;

import com.social_network.ua.entity.Community_Subscriber;

/**
 * Created by Rostyslav on 06.03.2017.
 */
public interface CommunitySubscriberDao {
    void add(Community_Subscriber community_subscriber);
    void delete(Community_Subscriber community_subscriber);
    boolean checkIfUserSubscribed(long userId,long communityId);
    Community_Subscriber findOneByUserIdAndCommunityId(long userId,long communityId);
}
