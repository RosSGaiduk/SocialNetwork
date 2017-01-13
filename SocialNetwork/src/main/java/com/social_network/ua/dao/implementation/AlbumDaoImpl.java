package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.AlbumDao;
import com.social_network.ua.entity.Album;
import com.social_network.ua.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Rostyslav on 12.01.2017.
 */
@Repository
public class AlbumDaoImpl implements AlbumDao{
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(Album album) {
        entityManager.persist(album);
    }

    @Transactional
    public void edit(Album album) {
        entityManager.merge(album);
    }

    @Transactional
    public void delete(Album album) {
        entityManager.remove(entityManager.contains(album) ? album : entityManager.merge(album));
    }

    @Transactional
    public Album findOne(long id) {
        return entityManager.find(Album.class,id);
    }

    @Transactional
    public Album findOneByNameAndUserId(String albumName, long userId) {
        return (Album) entityManager.createQuery("from Album where name = ?1 and user like ?2").setParameter(1,albumName).setParameter(2,entityManager.find(User.class,userId)).getSingleResult();
    }

    @Transactional
    public List<Album> findAll() {
        return entityManager.createQuery("from Album").getResultList();
    }
}
