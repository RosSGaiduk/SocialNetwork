package com.social_network.ua;

import com.social_network.ua.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Rostyslav on 21.11.2016.
 */
public class Main {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static void saveMp3File(String urlToFile,String urlWhereToSaveNewFile){
        File f = new File(urlToFile);
        InputStream is = null;
        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputStream outstream = null;
        try {
            outstream = new FileOutputStream(new File(urlWhereToSaveNewFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buffer = new byte[4096];
        int len;
        try {
            while ((len = is.read(buffer)) > 0) {
                outstream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outstream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

        /*User user = entityManager.find(User.class,1l);
        System.out.println(user.getUserImages());

        for (User_Images user_images: user.getUserImages())
            System.out.println(user_images.getUrlOfImage());

        Object[] images =  user.getUserImages().toArray();
        System.out.println(images.length);
        User_Images image = (User_Images) images[images.length-1];
        System.out.println(image.getUrlOfImage());*/



        //saveMp3File("C:/Users/Rostyslav/Desktop/Adele Hello.mp3","C:/Users/Rostyslav/Desktop/Adele1 - Hello.mp3");


        /*User user = entityManager.find(User.class,7l);
        System.out.println(user.getMusics().size());

        Music music = entityManager.find(Music.class,1l);
        System.out.println(music.getUsers().size());*/

        List<User> users = entityManager.createQuery("from User").getResultList();
        System.out.println(users.size());

        String [] names = {
                "Ross","Oleg","Name1"

        };


        Random randomForNames = new Random();

        String[] messages = {
          "Hello","Hi","How are you?","How old are you?",":)",":(","Good","Well",
                "I am "+(randomForNames.nextInt(80)+10),"My name is "+names[randomForNames.nextInt(names.length)],
                "I am "+names[randomForNames.nextInt(names.length)]
        };



        for (int i = 0; i < 100; i++){
            Random random = new Random();
            int indexFrom = random.nextInt(users.size());
            int indexTo = random.nextInt(users.size());
            String message = messages[random.nextInt(messages.length)];
            Message messageSend = new Message();
            messageSend.setUserFrom(users.get(indexFrom));
            messageSend.setUserTo(users.get(indexTo));
            messageSend.setText(message);
            messageSend.setDateOfMessage(new Date(System.currentTimeMillis()));
            entityManager.merge(messageSend);
        }


        //List<subscribers> subscriberses = entityManager.createQuery("from subscribers").getResultList();
        //System.out.println(subscriberses.size());


        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
