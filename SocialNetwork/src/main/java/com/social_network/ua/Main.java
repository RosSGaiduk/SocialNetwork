package com.social_network.ua;

import com.social_network.ua.controllers.AjaxController;
import com.social_network.ua.dao.implementation.ImageDaoImpl;
import com.social_network.ua.dao.implementation.LikeDaoImpl;
import com.social_network.ua.entity.*;
import com.social_network.ua.services.ImageService;
import com.social_network.ua.services.LikeService;
import com.social_network.ua.services.implementation.ImageServiceImpl;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
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



    public static void main(String[] args) throws IOException {
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


        /*LLike like = entityManager.find(LLike.class,7l);
        entityManager.remove(entityManager.contains(like)?like: entityManager.merge(like));*/

        //List<Object> objects = entityManager.createNativeQuery("select u.id from user_images u join album a where u.album_id = a.id and u.user_id = 1 and a.name = 'MY_PAGE_PHOTOS' group by u.id desc").getResultList();
        /*List<User_Images> user_images = new ArrayList<>(objects.size());

        for (int i = 0; i < objects.size(); i++){
            BigInteger bigInteger = (BigInteger) (objects.get(i));
            System.out.println(bigInteger.longValue());
            user_images.add(entityManager.find(User_Images.class,bigInteger.longValue()));
        }

        for (User_Images u: user_images)
            System.out.println(u.getAlbum());*/



        /*Object object = entityManager.createNativeQuery("select u.id from user_images u join album a where u.album_id = a.id and u.user_id = 1 and a.name = 'MY_PAGE_PHOTOS' and u.id < ?3 group by u.id desc").setParameter(3,137).setMaxResults(1).getSingleResult();
        BigInteger bigInteger = (BigInteger) object;
        System.out.println(bigInteger);*/

        /*List<User> users = entityManager.createQuery("select user from LLike l where l.userImage like ?1").setParameter(1,entityManager.find(User_Images.class,131l)).getResultList();
        System.out.println(users.size());*/


        /*Date date = new Date("2017/03/23 10:36:17");
        System.out.println(date);
        System.out.println(date.getTime());
        Date date1 = new Date(date.getTime());
        System.out.println(date1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String []str = ("2017/03/23 10:36:17").split("[/: ]");
        for (String string: str)
            System.out.println(Integer.parseInt(string));
        System.out.println(str.length);
        LocalDateTime localDate = LocalDateTime.of(Integer.parseInt(str[0]),Integer.parseInt(str[1]),Integer.parseInt(str[2]),
                Integer.parseInt(str[3]),Integer.parseInt(str[4]),Integer.parseInt(str[5]));
        String date2 = dtf.format(localDate);
        System.out.println(date2);*/


        /*//Works
        Long time = 1490258177000l;
        Date date = new Date("2017/03/25 19:36:17");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.now();
        CharSequence charSequence = "2017/03/25 19:36:17";
        TemporalAccessor dateThis = dtf.parse(charSequence);
        System.out.println(dateThis);
        String str = dateThis.toString().substring(19,dateThis.toString().length());
        str = str.replace('T',' ');
        str = str.replace('-','/');
        System.out.println(str);*/

        /*Long time = 1490258177000l;
        Date date = new Date(time);
        String str = DateFormatUtils.format(date, "yyyy/MM/dd hh:mm:ss");*/

       //System.out.println(str);


        /*Date date1 = new Date(System.currentTimeMillis());
        System.out.println(date1.getTime());
        long val = date1.getTime();
        Date date = new Date(date1.getTime());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("20yy/mm/dd HH:mm:ss");
        System.out.println(date.getTime());
        LocalDateTime localDate = LocalDateTime.of(date.getYear(),date.getMonth(),date.getDay(),date.getHours(),date.getMinutes(),date.getSeconds());
        String myDbDate = dtf.format(localDate);
        System.out.println(myDbDate);
        System.out.println(date.getMonth());*/

        /*Date lastOnline = new Date(System.currentTimeMillis());
        String timeToDb  = DateFormatUtils.format(lastOnline, "yyyy/MM/dd HH:mm:ss");
        System.out.println(timeToDb);*/



       /* Date date1 = new Date(myDbDate);
        System.out.println(date1.getTime()+" "+date.getTime());*/


        /*Album album = entityManager.find(Album.class,1l);
        System.out.println(album);
        Object o = entityManager.createNativeQuery("SELECT u.id FROM User_Images u WHERE u.album_id = ?1 and u.id < ?2 GROUP BY u.id DESC").setParameter(1,album.getId()).setParameter(2,163l).setMaxResults(1).getSingleResult();
        BigInteger bigInteger = (BigInteger) o;
        User_Images image = entityManager.find(User_Images.class,bigInteger.longValue());
        System.out.println(image.getId());*/


        /*String str = "Hello world";
        char c1 = str.charAt(0);
        char c2 = str.charAt(str.length()-1);
        str = str.substring(1,str.length()-1);
        str = c2+str+c1;
        System.out.println(str);*/

        //Завдання 3
        /*String str = "sad asdas siodijasod ofgofg asdaslk";
        if (str.charAt(str.length()-1)!=' ')
            str+=" ";

        List<String> words = new ArrayList<>();
        String word = "";
        //split by ' '
        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i)!=' ') {
                word += str.charAt(i);
            } else {
                if (word!="")
                words.add(word);
                word = "";
            }
        }

        String word1 = words.get(0);
        String word2 = words.get(words.size()-1);

        String newString = ""+word2+" ";
        for (int i = 1; i < words.size()-1; i++)
            newString+=words.get(i)+" ";
        newString+=word1;
        System.out.println(newString);



        //Завдання 1
        int []arr = {1,2,0,-10,-10,2,3,20,24,5};
        int element = arr[8];

        int count = 0;
        for (int i = 0; i < arr.length; i++){
            if (arr[i]<element)
                count++;
        }
        System.out.println("Index of this element: "+count);


        //Завдання 2
        LinkedList<Double> linkedList = new LinkedList<>();
        for (int i = 0; i < 10; i++){
            Random random = new Random();
            linkedList.add(new Double(random.nextInt(25)));
        }
        System.out.print("List: ");
        for (Double d: linkedList)
            System.out.print(d+" ");
        System.out.println();
        System.out.println("--------------------Sorted linked list");

        for (int i = 0; i < linkedList.size(); i++){
            for (int j = i; j < linkedList.size(); j++){
                if (linkedList.get(i)>linkedList.get(j)){
                    Double val = linkedList.get(i);
                    linkedList.set(i,linkedList.get(j));
                    linkedList.set(j,val);
                }
            }
        }

        for (Double d: linkedList)
            System.out.print(d+" ");*/


       /* for (int i = 0; i < doubles.length; i++){
            for (int j = i; j < doubles.length; j++){
                if (doubles[i]>doubles[j]){
                    Double d = doubles[i];
                    doubles[i] = doubles[j];
                    doubles[j] = d;
                }
            }
        }
        for (int i = 0; i < doubles.length; i++){
            Double d = linkedList.get(i);
            d = 111d;
            System.out.println(linkedList.get(i));
        }*/



        /*File file = new File("/resources/users");
        System.out.println(file.listFiles().length);*/
        //BufferedImage image = ImageIO.read(new File("path"));

        /*Date date1 = new Date(System.currentTimeMillis());
        System.out.println(date1.getTime());*/

        /*String str = "sadasd";
        char firstSymbol = str.charAt(0);
        firstSymbol = Character.toUpperCase(firstSymbol);
        str = str.substring(1,str.length());
        str = firstSymbol+str;
        System.out.println(str);*/
        //entityManager.createNativeQuery("INSERT INTO User_Video(video_id,user_id) VALUES (?1,?2)").setParameter(1,20l).setParameter(2,1l).executeUpdate();
        /*User user = entityManager.find(User.class,1l);
        System.out.println(user.getVideos().size());*/

        /*
        String newStr = ""+c2;
        for (int i = 1; i < str.length()-1; i++)
            newStr+=str.charAt(i);
        newStr+=c1;
        System.out.println(newStr);
        str.*/
        User_Images user_images = (User_Images) entityManager.createQuery("from User_Images where user_id = ?1 and id>?2 group by id").setParameter(1,1l).setParameter(2,215l).setMaxResults(1).getSingleResult();
        System.out.println(user_images.getId());
        entityManager.getTransaction().commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
