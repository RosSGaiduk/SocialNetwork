package com.social_network.ua;

import com.social_network.ua.controllers.AjaxController;
import com.social_network.ua.dao.implementation.ImageDaoImpl;
import com.social_network.ua.dao.implementation.LikeDaoImpl;
import com.social_network.ua.entity.*;
import com.social_network.ua.services.ImageService;
import com.social_network.ua.services.LikeService;
import com.social_network.ua.services.implementation.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by Rostyslav on 21.11.2016.
 */
public class Main {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;
    private static ImageDaoImpl imageDao = new ImageDaoImpl();
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

    /*private static int searchElement(int val,int []arr){
        int firstIndex = 0;
        int lastIndex = arr.length-1;
        int middleIndex = (firstIndex+lastIndex)/2;

        if (val==arr[lastIndex]){
            return lastIndex;
        }
        while (val!=arr[middleIndex]) {
            if (arr[middleIndex] < val) {
                firstIndex = middleIndex;
                middleIndex+=(lastIndex-firstIndex)/2;
            } else {
                middleIndex = (firstIndex+middleIndex)/2;
            }
        }
        return middleIndex;
    }*/

    private static int searchElement(int val,int []arr,int firstIndex,int middleIndex){
        int lastIndex = arr.length-1;
        if (val==arr[lastIndex]){
            return lastIndex;
        }
        if (val!=arr[middleIndex]) {
            if (arr[middleIndex] < val) {
                firstIndex = middleIndex;
                middleIndex+=(lastIndex-firstIndex)/2;
            } else {
                middleIndex = (firstIndex+middleIndex)/2;
            }
            return searchElement(val,arr,firstIndex,middleIndex);
        }
        return middleIndex;
    }






    private static int searchElement(int val,int []arr){
            int firstIndex = 0;
            int lastIndex = arr.length-1;
            int middleIndex = (firstIndex+lastIndex)/2;
        return searchElement(val,arr,firstIndex,middleIndex);
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


        /*List<User> users = entityManager.createQuery("from User").getResultList();

        for (int i = 0; i < 1000; i++){
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
        }*/

        /*List<Message> messages = entityManager.createQuery("from Message where id>?3 and (userFrom_id = ?1 and userTo_id = ?2)  or (userTo_id = ?1 and userFrom_id = ?2) order by id desc").setParameter(1,1).setParameter(2,7).setParameter(3,10l).setMaxResults(50).getResultList();
        System.out.println(messages.size());*/

        /*Object val = entityManager.createQuery("select max(id) from Message where (userFrom_id = ?1 and userTo_id = ?2) or (userTo_id = ?1 and userFrom_id = ?2) order by id").setParameter(1,1l).setParameter(2,7l).getSingleResult();
        System.out.println((long)val);
        */

        //List<Message> messages =  entityManager.createQuery("from Message where (userFrom_id = ?1 and userTo_id = ?2)  or (userTo_id = ?1 and userFrom_id = ?2) and id>?3 order by id DESC").setParameter(1,1l).setParameter(2,7l).setParameter(3,22917l).getResultList();
        //List<Message> messages =  entityManager.createQuery("from Message where id>?3 group by id").setParameter(3,22917l).getResultList();

        /*List<Object[]> objects = entityManager.createNativeQuery("select id from Message m where ((m.userFrom_id = ?1 and m.userTo_id = ?2) or (m.userFrom_id = ?2 and m.userTo_id = ?1)) and m.id>?3 group by id DESC").setParameter(1,1l).setParameter(2,8l).setParameter(3,22917l).getResultList();
        List<Message> messages = new ArrayList<>(objects.size());
        for (Object o: objects)
        {
            BigInteger bigInteger = (BigInteger)o;
            Message m = entityManager.find(Message.class,bigInteger.longValue());
            messages.add(m);
        }
        System.out.println(messages.size());*/

        //System.out.println(objects.size());

        //System.out.println(args instanceof Object);

        //System.out.println(messages.size());
        /*Integer int2 = new Integer(42);
        Double d1 = 1d;
        Double d2 = 1d;
        double d3 = 1;
        double d4 = 1;
        System.out.println(d1==d2);
        System.out.println(d3==d4);
        System.out.println(d1==d3);*/

        //User user = entityManager.find(User.class,1l);

        //List<Message> messages = entityManager.createQuery("from Message where userFrom = ?1 group by userTo.id").setParameter(1,user).getResultList();

        /*List<Message> messages = entityManager.createQuery("from Message where userFrom = ?1 group by userTo.id").setParameter(1,user).getResultList();

        for (Message message: messages)
            System.out.println(message.getUserFrom().getId()+" "+message.getUserTo().getId());*/

        /*List<Message> messages = entityManager.createQuery("from Message where userFrom =?1 group by userTo.id").setParameter(1,user).getResultList();
        List<Message> anotherCase = entityManager.createQuery("from Message where userTo =?1 group by userFrom.id").setParameter(1,user).getResultList();
        Set<Message> messageSet = new TreeSet<>();
        for (Message m: messages)
            messageSet.add(m);
        for (Message m:anotherCase)
            messageSet.add(m);

        for (Message m:messageSet)
            System.out.println(m.getUserFrom().getId()+" "+m.getUserTo().getId());*/

        /*String str = "Hello world";
        str = str.substring(0,5);
        System.out.println(str);*/

        /*int []arr = new int[1000];
        for (int i = 0; i < arr.length; i++){
            Random random = new Random();
            arr[i] = random.nextInt(20000);
            //System.out.println(arr[i]);
        }

        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
        System.out.println("**********************");
        for (int i = 0; i < arr.length; i++){
            System.out.println("val: "+arr[i]+" index: "+searchElement(arr[i],arr));
        }

        int a = 10;
        int b = 11;

        int newVal = (a>b) ? a : b;
        System.out.println(newVal);
*/
        /*for (int val: arr) {
            System.out.println("val: "+val+" index: "+searchElement(val, arr));
        }*/


        /*AjaxController[] strings = new AjaxController[10];
        Arrays.sort(strings);

        for (AjaxController str: strings)
            System.out.println(str);*/


        /*List<Object[]> objects = entityManager.createNativeQuery("select u.id from User u JOIN Community_Subscriber c on u.id = c.subscriber_id and c.community_id = ?1").setParameter(1,5l).getResultList();

        for (Object o: objects){
            BigInteger bigInteger = (BigInteger) o;
            System.out.println(bigInteger.longValue());
            System.out.println(entityManager.find(User.class,bigInteger.longValue()).getEmail());
        }*/
        /*User user = entityManager.find(User.class,1l);
        entityManager.createNativeQuery("UPDATE Message set newestUserFromUrlImagePattern = ?1 where userFrom_id = ?2").setParameter(1,"").setParameter(2,user.getId()).executeUpdate();
*/

        /*BigInteger val = (BigInteger) entityManager.createNativeQuery("SELECT min(id) from Message m where ((m.userFrom_id = ?1 and m.userTo_id = ?2) or (m.userFrom_id = ?2 and m.userTo_id = ?1))").setParameter(1,1l).setParameter(2,7l).getSingleResult();
        System.out.println(val.longValue());*/


       /* List<BigInteger> ids = entityManager.createNativeQuery("SELECT c.music_id from Community_Music c where c.community_id = ?1 ORDER BY c.music_id DESC").setParameter(1,5l).getResultList();

        for (BigInteger i: ids)
            System.out.println(i.longValue());*/

        //User_Images user_images = entityManager.find(User_Images.class,111l);
        //entityManager.remove(entityManager.contains(user_images)? user_images: entityManager.merge(user_images));
        //entityManager.remove(user_images);


        LLike like = entityManager.find(LLike.class,7l);
        entityManager.remove(entityManager.contains(like)?like: entityManager.merge(like));

        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
