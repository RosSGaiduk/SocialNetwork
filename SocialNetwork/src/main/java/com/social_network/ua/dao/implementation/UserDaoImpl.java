package com.social_network.ua.dao.implementation;

import com.social_network.ua.dao.UserDao;
import com.social_network.ua.entity.Music;
import com.social_network.ua.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rostyslav on 21.11.2016.
 */
@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext(name = "Main")
    private EntityManager entityManager;

    @Transactional
    public void add(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Transactional
    public void edit(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public User findOne(long id) {
        return entityManager.find(User.class,id);
    }

    @Transactional
    public void addFriendToUser(long idOfUser, long idOfFriend) {
        User user = entityManager.find(User.class,idOfUser);
        user.getFriends().add(entityManager.find(User.class,idOfFriend));
    }

    @Transactional
    public void addMusicToUser(long idOfUser, long idOfMusic) {
        User user = entityManager.find(User.class,idOfUser);
        user.getMusics().add(entityManager.find(Music.class,idOfMusic));
    }



    @Transactional
    public List<User> findAllThatArentFriendsOfUserAndArentSubscribersOfUser(Long[]idsOf) {
        String query = "";
        for (int i = 0; i < idsOf.length-1; i++){
            query+="id != "+idsOf[i]+" and ";
        }
        query+="id != "+idsOf[idsOf.length-1];
        return entityManager.createQuery("from User where "+query).getResultList();
    }

    @Transactional
    public List<Long> findAllIdsOfSubscribersOfUser(long id) {
        try {
            List<Long> ids = entityManager.createQuery("select subscriber_id from subscribersCopy where user_id = ?1").setParameter(1, id).getResultList();
            return ids;
        } catch (Exception ex){
            System.out.println("Exception");
            return null;
        }
    }

    @Transactional
    public List<User> findAllByInput(String str) {
        List<User> users = entityManager.createQuery("from User where firstName like ?1 or lastName like ?1").setParameter(1,"%"+str+"%").getResultList();
        System.out.println(users.size()==0);
        if (users.size()==0) {
            try {
                users = entityManager.createQuery("from User where (firstName like ?1 and lastName like ?2) or (firstName like ?2 and lastName like ?1)").setParameter(1, str.split(" ")[0]).setParameter(2, str.split(" ")[1]).getResultList();
                if (users.size()==0)
                    users = entityManager.createQuery("from User where (firstName like ?1 and lastName like ?2) or (lastName like ?1 and firstName like ?2)").setParameter(1,str.split(" ")[0]).setParameter(2,"%"+str.split(" ")[1]+"%").getResultList();
            } catch (Exception ex) {
                System.out.println("Exception from method findAllByInput(String str) from UserDaoImpl");
            }
        }
        return users;
    }

    @Transactional
    public User selectUser(long id1, long id2) {
        return (User)entityManager.createQuery("from subscribersCopy where subscriber_id = ?1 and user_id = ?2").setParameter(1,id1).setParameter(2,id2).getSingleResult();
    }

    @Transactional
    public List<User> findAll() {
        return entityManager.createQuery("from User").getResultList();
    }
}
