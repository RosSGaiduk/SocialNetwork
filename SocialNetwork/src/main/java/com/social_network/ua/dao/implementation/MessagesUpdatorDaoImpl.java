package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.MessagesUpdatorDao;
import com.social_network.ua.entity.MessagesUpdator;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Rostyslav on 11.12.2016.
 */
@Repository
public class MessagesUpdatorDaoImpl implements MessagesUpdatorDao{
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(MessagesUpdator messagesUpdator) {
        entityManager.persist(messagesUpdator);
    }

    @Transactional
    public void delete(MessagesUpdator messagesUpdator) {
        entityManager.remove(entityManager.contains(messagesUpdator) ? messagesUpdator : entityManager.merge(messagesUpdator));
    }
    @Transactional
    public void edit(MessagesUpdator messagesUpdator) {
        entityManager.merge(messagesUpdator);
    }
    @Transactional
    public MessagesUpdator findOne(long id) {
        return entityManager.find(MessagesUpdator.class,id);
    }

    @Transactional
    public boolean findMessageBetweenUsers(long id1, long id2) {
        Object o = entityManager.createQuery("select count(id) from MessagesUpdator where idUserFrom = ?1 and idUserTo = ?2").setParameter(1,id1).setParameter(2,id2).getSingleResult();
        Long count = (Long)o;
        if (count!=0) return true;
        return false;
    }

    @Transactional
    public void deleteWhereUserFromLikeId1AndUserToLikeId2(long id1, long id2) {
        MessagesUpdator messagesUpdator = (MessagesUpdator)entityManager.createQuery("from MessagesUpdator where idUserFrom = ?1 and idUserTo = ?2").setParameter(1,id1).setParameter(1,id2).getSingleResult();
        delete(messagesUpdator);
    }

    @Transactional
    public List<MessagesUpdator> findAll() {
        return entityManager.createQuery("from MessagesUpdator").getResultList();
    }
}
