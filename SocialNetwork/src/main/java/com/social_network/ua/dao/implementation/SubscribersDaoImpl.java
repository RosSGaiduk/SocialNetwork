package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.SubscribersDao;
import com.social_network.ua.entity.subscribersCopy;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 * Created by Rostyslav on 23.01.2017.
 */
@Repository
public class SubscribersDaoImpl implements SubscribersDao {
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(subscribersCopy subscribers) {
        entityManager.persist(subscribers);
    }

    @Transactional
    public void delete(subscribersCopy subscribers) {
        entityManager.remove(subscribers);
    }

    @Transactional
    public boolean checkIfFriends(long id1, long id2) {
        int count = (Integer) entityManager.createQuery("select count(id) from subscribersCopy  where (user_id = ?1 and subscriber_id = ?2) or (user_id = ?2 and subscriber_id = ?1)").setParameter(1,id1).setParameter(2,id2).getSingleResult();
        if (count == 2) return true;
        else return false;
    }
}
