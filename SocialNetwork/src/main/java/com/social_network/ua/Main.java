package com.social_network.ua;

import com.social_network.ua.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.util.*;

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

        /*List<User> users = entityManager.createQuery("from User").getResultList();
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
        }*/


        //List<subscribers> subscriberses = entityManager.createQuery("from subscribers").getResultList();
        //System.out.println(subscriberses.size());

        /*List<Object[]> objects = entityManager.createNativeQuery("select * from user_music join Music on music.id = music_id and user_id = 1 ORDER BY id desc").setMaxResults(1).getResultList();
        List<Music> getSongs = new ArrayList<>(objects.size());
        for (Object o[]: objects){
            Music m = entityManager.find(Music.class,Long.parseLong(o[1].toString()));
            getSongs.add(m);
        }

        for (Music m: getSongs)
            System.out.println(m.getNameOfSong());*/
        //User u  =(User)entityManager.createQuery("select userFrom from Message m where m.id = ?1").setParameter(1,1560l).getSingleResult();
        //System.out.println(u.getId());


        List<User> users = entityManager.createQuery("from User").getResultList();

        for (int i = 0; i < 10000; i++){
            Message message = new Message();
            message.setDateOfMessage(new Date(System.currentTimeMillis()));
            Random random = new Random();
            int size = random.nextInt(500)+5;
            String str = "";
            for (int j = 0; j < size; j++) {
                str += (char) (random.nextInt(92)+33);
            }
            message.setText(str);
            message.setUserFrom(entityManager.find(User.class,users.get(random.nextInt(users.size())).getId()));
            message.setUserTo(entityManager.find(User.class,users.get(random.nextInt(users.size())).getId()));
            entityManager.persist(message);
            System.out.println("LEFT: "+(10000-i));
            System.out.println("--------------------------------------------");
            System.out.println("Message: "+message.getText());
            //System.out.println("User from: "+message.getUserFrom().getFirstName());
            //System.out.println("User to: "+message.getUserTo().getFirstName());
            System.out.println("--------------------------------------------");
        }

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
