package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.RecordDao;
import com.social_network.ua.entity.Record;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
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
        entityManager.remove(record);
    }

    @Transactional
    public Record findOne(long id) {
        return entityManager.find(Record.class,id);
    }

    @Transactional
    public List<Record> findAll() {
        return entityManager.createQuery("from Record").getResultList();
    }
}
