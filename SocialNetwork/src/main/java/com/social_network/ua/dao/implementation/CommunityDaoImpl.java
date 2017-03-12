package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.CommunityDao;
import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Rostyslav on 06.03.2017.
 */
@Repository
public class CommunityDaoImpl implements CommunityDao{
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(Community community) {
        entityManager.persist(community);
    }

    @Transactional
    public void delete(Community community) {
        entityManager.remove(community);
    }

    @Transactional
    public void edit(Community community) {
        entityManager.merge(community);
    }

    @Transactional
    public Community findOne(long id) {
        return entityManager.find(Community.class,id);
    }

    @Transactional
    public User findUserOfCommunity(long idCommunity) {
        User user = (User)entityManager.createQuery("select user from Community  c where c.id = ?1").setParameter(1,idCommunity).getSingleResult();
        return user;
    }

    @Transactional
    public List<Community> findAll() {
        return entityManager.createQuery("from Community").getResultList();
    }
}
