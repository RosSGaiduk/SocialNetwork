package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.CommentDao;
import com.social_network.ua.entity.Comment;
import com.social_network.ua.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rostyslav on 14.03.2017.
 */
@Repository
public class CommentDaoImpl implements CommentDao{
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;


    @Transactional
    public void add(Comment comment) {
        System.out.println("adding a comment"+comment==null);
        entityManager.persist(comment);
    }

    @Transactional
    public void edit(Comment comment) {
        entityManager.merge(comment);
    }

    @Transactional
    public void delete(Comment comment) {
        entityManager.remove(entityManager.contains(comment)? comment: entityManager.merge(comment));
    }

    @Transactional
    public Comment findOne(long id) {
        return entityManager.find(Comment.class,id);
    }

    @Transactional
    public void updateCommentsNewestImageSrcOfUser(User user) {
        entityManager.createQuery("update Comment set userFromNewestUrlImage = ?2 where user like ?1").setParameter(1,user).setParameter(2,user.getNewestImageSrc()).executeUpdate();
    }

    @Transactional
    public List<Comment> findAllByImageId(long imageId) {
        List<Comment> comments = entityManager.createQuery("from Comment where userImage.id = ?1 group by id").setParameter(1,imageId).getResultList();
        Collections.reverse(comments);
        return comments;
    }

    @Transactional
    public List<Comment> findAll() {
        return entityManager.createQuery("from Comment").getResultList();
    }
}
