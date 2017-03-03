package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.MessageDao;
import com.social_network.ua.entity.Message;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rostyslav on 21.11.2016.
 */
@Repository
public class MessageDaoImpl implements MessageDao {

    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(Message message) {
        entityManager.persist(message);
    }

    @Transactional
    public void edit(Message message) {
        entityManager.merge(message);
    }

    @Transactional
    public void delete(Message message) {
        entityManager.remove(message);
    }

    @Transactional
    public Message findOne(long id) {
        return entityManager.find(Message.class,id);
    }

    @Transactional
    public long findAllLastBy2ids(long id1,long id2) {
        Object count = entityManager.createQuery("select count(id) from Message where (userFrom_id = ?1 and userTo_id = ?2) or (userTo_id = ?1 and userFrom_id = ?2) order by id desc").setParameter(1,id1).setParameter(2,id2).getSingleResult();
        return (long) count;
    }

    @Transactional
    public long findAllByIds(long id1,long id2) {
        Object count = entityManager.createQuery("select count(id) from Message where (userFrom_id = ?1 and userTo_id = ?2) or (userFrom_id = ?2 and userTo_id = ?1)").setParameter(1,id1).setParameter(2,id2).getSingleResult();
        return (long) count;
    }

    @Transactional
    public long findLastIdOfMessageBetweenUsers(long id1, long id2) {
        try{
            return (long)entityManager.createQuery("select max(id) from Message where (userFrom_id = ?1 and userTo_id = ?2) or (userTo_id = ?1 and userFrom_id = ?2) order by id").setParameter(1,id1).setParameter(2,id2).getSingleResult();
        } catch (Exception ex){
            return 0;
        }
    }

    @Transactional
    public List<Message> findAllByIdsAndMaxId(long id1, long id2, long maxId) {
        List<Object[]> objects = entityManager.createNativeQuery("select id from Message m where ((m.userFrom_id = ?1 and m.userTo_id = ?2) or (m.userFrom_id = ?2 and m.userTo_id = ?1)) and m.id>?3 group by id DESC").setParameter(1,id1).setParameter(2,id2).setParameter(3,maxId).setMaxResults(50).getResultList();
        List<Message> messages = new ArrayList<>(objects.size());
        for (Object o: objects)
        {
            BigInteger bigInteger = (BigInteger)o;
            Message m = entityManager.find(Message.class,bigInteger.longValue());
            messages.add(m);
        }
        System.out.println(messages.size());
        return messages;
    }

    @Transactional
    public List<Message> findAllByIdsAndMinId(long id1, long id2, long minId) {
        List<Object[]> objects = entityManager.createNativeQuery("select id from Message m where ((m.userFrom_id = ?1 and m.userTo_id = ?2) or (m.userFrom_id = ?2 and m.userTo_id = ?1)) and m.id<?3 group by id DESC").setParameter(1,id1).setParameter(2,id2).setParameter(3,minId).setMaxResults(50).getResultList();
        List<Message> messages = new ArrayList<>(objects.size());
        for (Object o: objects)
        {
            BigInteger bigInteger = (BigInteger)o;
            Message m = entityManager.find(Message.class,bigInteger.longValue());
            messages.add(m);
        }
        System.out.println(messages.size());
        return messages;
    }


    @Transactional
    public List<Message> findAll() {
        return entityManager.createQuery("from Message").getResultList();
    }
}
