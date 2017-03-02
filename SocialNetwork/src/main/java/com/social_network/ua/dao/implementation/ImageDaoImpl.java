package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.ImageDao;
import com.social_network.ua.entity.Album;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rostyslav on 24.11.2016.
 */
@Repository
public class ImageDaoImpl implements ImageDao {
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;


    @Transactional
    public void add(User_Images image) {
     entityManager.persist(image);
    }

    @Transactional
    public void edit(User_Images image) {
        entityManager.merge(image);
    }

    @Transactional
    public void delete(User_Images image) {
        entityManager.remove(image);
    }

    @Transactional
    public User_Images findOne(long id) {
        return entityManager.find(User_Images.class,id);
    }

    @Transactional
    public List<User_Images> findAllByAlbum(Album album) {
        List<User_Images> images = entityManager.createQuery("from User_Images where album like ?1 group by id").setParameter(1,album).getResultList();
        Collections.reverse(images);
        return images;
    }

    @Transactional
    public List<User_Images> findAllByUser(User user) {
        List<User_Images> images = entityManager.createQuery("from User_Images where user like ?1 group by id").setParameter(1,user).getResultList();
        Collections.reverse(images);
        return images;
    }

    @Transactional
    public List<User_Images> fundAll() {
        return entityManager.createQuery("from User_Images").getResultList();
    }
}
