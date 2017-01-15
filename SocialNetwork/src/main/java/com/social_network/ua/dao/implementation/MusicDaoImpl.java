package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.MusicDao;
import com.social_network.ua.entity.Music;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Rostyslav on 01.12.2016.
 */
@Repository
public class MusicDaoImpl implements MusicDao {

    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(Music music) {
        entityManager.persist(music);
    }

    @Transactional
    public void edit(Music music) {
        entityManager.merge(music);
    }

    @Transactional
    public void delete(Music music) {
        entityManager.remove(music);
    }

    @Transactional
    public Music findOne(long id) {
        return entityManager.find(Music.class,id);
    }

    @Transactional
    public List<Music> findAll() {
        return entityManager.createQuery("from Music").getResultList();
    }
}