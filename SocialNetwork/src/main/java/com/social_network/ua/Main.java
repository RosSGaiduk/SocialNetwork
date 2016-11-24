package com.social_network.ua;

import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;

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

        //User user = entityManager.find(User.class,8l);


        //System.out.println(user.getFirstName());

        //user1.getFriends().add(user);
        //user.getFriends().add(user1);

        //entityManager.merge(user);
        //entityManager.merge(user1);

        /*System.out.println(user.getSubscribers().size());
        System.out.println(user.getFriends().size());*/

        User user = entityManager.find(User.class,1l);
        System.out.println(user.getUserImages());

        for (User_Images user_images: user.getUserImages())
            System.out.println(user_images.getUrlOfImage());

        Object[] images =  user.getUserImages().toArray();
        System.out.println(images.length);
        User_Images image = (User_Images) images[images.length-1];
        System.out.println(image.getUrlOfImage());



        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
