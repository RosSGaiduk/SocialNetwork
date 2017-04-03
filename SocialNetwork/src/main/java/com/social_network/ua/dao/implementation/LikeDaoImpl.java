package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.LikeDao;
import com.social_network.ua.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Rostyslav on 15.03.2017.
 */
@Repository
public class LikeDaoImpl implements LikeDao{
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(LLike like) {
        entityManager.persist(like);
    }

    @Transactional
    public void delete(LLike like) {
        entityManager.remove(entityManager.contains(like) ? like: entityManager.merge(like));
    }

    @Transactional
    public void edit(LLike like) {
        entityManager.merge(like);
    }

    @Transactional
    public LLike findOne(long id) {
        return entityManager.find(LLike.class,id);
    }

    @Transactional
    public LLike findOneByUserAndImage(User userFrom, User_Images image) {
        try {
            LLike like = (LLike) entityManager.createQuery("from LLike where user = ?1 and userImage = ?2").setParameter(1,userFrom).setParameter(2,image).getSingleResult();
            return like;
        } catch (Exception ex){
            return null;
        }
    }

    @Transactional
    public List<LLike> findAll() {
        return entityManager.createQuery("from LLike").getResultList();
    }

    @Transactional
    public LLike findOneByVideoAndUser(Video video, User user) {
        try{
            LLike lLike = (LLike)entityManager.createQuery("from LLike where user = ?1 and video = ?2").setParameter(1,user).setParameter(2,video).getSingleResult();
            return lLike;
        } catch (Exception ex){
            return null;
        }
    }
}
