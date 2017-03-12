package com.social_network.ua.services.implementation;

import com.social_network.ua.dao.CommunitySubscriberDao;
import com.social_network.ua.entity.Community_Subscriber;
import com.social_network.ua.services.CommunityService;
import com.social_network.ua.services.CommunitySubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rostyslav on 06.03.2017.
 */
@Service
public class CommunitySubscriberServiceImpl implements CommunitySubscriberService{
    @Autowired
    private CommunitySubscriberDao communitySubscriberDao;

    @Override
    public void add(Community_Subscriber community_subscriber) {
        communitySubscriberDao.add(community_subscriber);
    }
    @Override
    public void delete(Community_Subscriber community_subscriber) {
        communitySubscriberDao.delete(community_subscriber);
    }

    @Override
    public boolean checkIfUserSubscribed(long userId, long communityId) {
        return communitySubscriberDao.checkIfUserSubscribed(userId,communityId);
    }

    @Override
    public Community_Subscriber findOneByUserIdAndCommunityId(long userId, long communityId) {
        return communitySubscriberDao.findOneByUserIdAndCommunityId(userId,communityId);
    }
}
