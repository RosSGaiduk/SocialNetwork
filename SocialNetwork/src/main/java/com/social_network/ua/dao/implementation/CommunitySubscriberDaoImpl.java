package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.CommunitySubscriberDao;
import com.social_network.ua.entity.Community_Subscriber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by Rostyslav on 06.03.2017.
 */
@Repository
public class CommunitySubscriberDaoImpl implements CommunitySubscriberDao{
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(Community_Subscriber community_subscriber) {
        entityManager.persist(community_subscriber);
    }

    @Transactional
    public void delete(Community_Subscriber community_subscriber) {
        entityManager.remove(entityManager.contains(community_subscriber) ? community_subscriber : entityManager.merge(community_subscriber));
    }

    @Transactional
    public boolean checkIfUserSubscribed(long userId, long communityId) {
        try {
            Community_Subscriber community_subscriber = (Community_Subscriber) entityManager.createQuery("from Community_Subscriber where subscriber_id = ?1 and community_id = ?2").setParameter(1, userId).setParameter(2, communityId).getSingleResult();
            if (community_subscriber!=null)
            return true;
            else return false;
        } catch (Exception ex){
            return false;
        }
    }

    @Transactional
    public Community_Subscriber findOneByUserIdAndCommunityId(long userId, long communityId) {
        Community_Subscriber community_subscriber = null;
        try {
            community_subscriber = (Community_Subscriber) entityManager.createQuery("from Community_Subscriber where subscriber_id = ?1 and community_id = ?2").setParameter(1, userId).setParameter(2, communityId).getSingleResult();
            return community_subscriber;
        } catch (Exception ex){
            return null;
        }
    }
}
