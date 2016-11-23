package com.social_network.ua;

import com.social_network.ua.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Rostyslav on 21.11.2016.
 */
public class Main {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    public static void main(String[] args) {
        entityManagerFactory = Persistence.createEntityManagerFactory("Main");
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        User user = entityManager.find(User.class,8l);


        System.out.println(user.getFirstName());

        //user1.getFriends().add(user);
        //user.getFriends().add(user1);

        //entityManager.merge(user);
        //entityManager.merge(user1);

        System.out.println(user.getSubscribers().size());
        System.out.println(user.getFriends().size());


        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
