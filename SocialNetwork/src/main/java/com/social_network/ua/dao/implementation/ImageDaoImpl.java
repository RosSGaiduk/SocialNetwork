package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.ImageDao;
import com.social_network.ua.entity.Album;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.enums.AlbumName;
import com.social_network.ua.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rostyslav on 24.11.2016.
 */
@Repository
public class ImageDaoImpl implements ImageDao {
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;
    @Autowired
    private AlbumService albumService;


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
    public User_Images getPreviousImageFromMainAlbum(long userId, long id) {
        try {
            Object object = entityManager.createNativeQuery("select u.id from user_images u join album a where u.album_id = a.id and u.user_id = ?1 and a.name = 'MY_PAGE_PHOTOS' and u.id < ?3 group by u.id desc").setParameter(1,userId).setParameter(3,id).setMaxResults(1).getSingleResult();
            BigInteger bigInteger = (BigInteger) object;
            return findOne(bigInteger.longValue());
        } catch (Exception ex){
            /*System.out.println("ex");
            Album album = albumService.findOneByNameAndUserId(AlbumName.MY_PAGE_PHOTOS.toString(),userId);
            System.out.println("Album "+album.getName()+" "+album.getUser().getId());
            Object object = entityManager.createNativeQuery("select max(u.id) from user_images u where u.user_id = ?1 and u.album_id = ?2").setParameter(1,userId).setParameter(2,album.getId()).setMaxResults(1).getSingleResult();
            BigInteger bigInteger = (BigInteger) object;
            return findOne(bigInteger.longValue());*/
            System.out.println("ex");
            return null;
        }
    }

    @Transactional
    public User_Images getPreviousImageFromAlbum(Album album, long idImage) {
        try {
            Object o = entityManager.createNativeQuery("SELECT u.id FROM User_Images u WHERE u.album_id = ?1 and u.id < ?2 GROUP BY u.id DESC").setParameter(1,album.getId()).setParameter(2,idImage).setMaxResults(1).getSingleResult();
            BigInteger bigInteger = (BigInteger) o;
            return findOne(bigInteger.longValue());
        } catch (Exception ex){
            return (User_Images) entityManager.createQuery("from User_Images u where u.album like ?1 and u.id = (select max (id) from User_Images)").setParameter(1,album).setMaxResults(1).getSingleResult();
        }
    }

    @Transactional
    public User_Images findByPath(String path) {
        try {
            return (User_Images) entityManager.createQuery("from User_Images where urlOfImage = ?1").setParameter(1, path).getSingleResult();
        } catch (Exception ex){
            return null;
        }
    }

    @Transactional
    public User_Images findOneByUserIdAndName(User user, String urlImage) {
        try {
            User_Images user_images = (User_Images) entityManager.createQuery("from User_Images where user = ?1 and urlOfImage = ?2").setParameter(1, user).setParameter(2, urlImage).getSingleResult();
            return user_images;
        } catch (Exception ex){
            return null;
        }
    }

    @Transactional
    public List<User_Images> fundAll() {
        return entityManager.createQuery("from User_Images").getResultList();
    }
}
