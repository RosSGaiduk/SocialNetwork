package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.RecordDao;
import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.Record;
import com.social_network.ua.entity.User;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rostyslav on 26.11.2016.
 */
@Repository
public class RecordDaoImpl implements RecordDao {

    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(Record record) {
        entityManager.persist(record);
    }

    @Transactional
    public void edit(Record record) {
        entityManager.merge(record);
    }

    @Transactional
    public void delete(Record record) {
        //entityManager.remove(record);
        entityManager.remove(entityManager.contains(record) ? record : entityManager.merge(record));
    }

    @Transactional
    public Record findOne(long id) {
        return entityManager.find(Record.class,id);
    }

    @Transactional
    public List<Record> findAll() {
        return entityManager.createQuery("from Record").getResultList();
    }

    @Transactional
    public List<Record> findAllByCommunity(Community community) {
        List<Record> records = entityManager.createQuery("from Record where community = ?1 group by id").setParameter(1,community).getResultList();
        Collections.reverse(records);
        return records;
    }

    @Transactional
    public void updateUserImageSrcOfRecords(User user) {
        entityManager.createNativeQuery("UPDATE Record SET urlUserImagePattern = ?2 where userFrom_id = ?1").setParameter(1,user).setParameter(2,user.getNewestImageSrc()).executeUpdate();
    }


    @Transactional
    public List<Record> findAllInTheWallOf(long id) {
        return entityManager.createQuery("from Record where user_id = ?1 group by dateOfRecord").setParameter(1,id).getResultList();
    }
}
