package com.social_network.ua.controllers;

/**
 * Created by Rostyslav on 21.11.2016.
 */

import com.social_network.ua.entity.Comment;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.Video;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Rostyslav on 05.11.2016.
 */
public abstract class BaseMethods {
    public String stringUTF_8Encode(String str){
        try {
            str = new String(str.getBytes("ISO-8859-1"),"UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    public Set<User> friendsOfAuthentication(User user){
        //формуємо список всіх користувачів, на які підписався даний користувач(авторизований користувач)
        //ці користувачі МОЖУТЬ бути його друзями, а можуть зберігати його в підписниках
        Set<User> userOfThisSet = user.getFriends();
        //нам потрібно передати на сторінку всіх друзів даного користувача.
        //якщо ми просто передамо user.getFriends(); на сторінку, то серед цих "друзів" можуть трапитись
        //ті, на які даний користувач підписався, але вони заявку в друзі не прийняли.
        //бо в мене схема добавлення в друзі така: якщо user1 добавляє user2 в друзі, то user2 автоматично стає
        //другом user1, але user1 не буде другом user2, а тільки підписником. Якщо ж user2 так само після того
        //добавить в друзі user1, лише тоді вони будуть друзями між собою. Тому мені потрібно запрограмувати
        //передачу на сторінку юзерів, які по справжньому є друзями даного користувача.
        //роблю treeset, бо при зв'язку many to many появляються баги і воно виводило по 20 разів 1 друга на сторінці

        //в цей список будуть збережені такі юзери
        Set<User> friendsWhichAcceptedUserApplication = new TreeSet<>();
        //спочатку пробігаємся циклом по всіх користувачах з тих, на які підписався даний користувач(залогінований)
        for (User u: userOfThisSet){
            //з кожного з них дістаєм ДРУЗІВ...
            Set<User> friendsOfUTreeSet =  u.getFriends();
            //перебираємо їх
            for (User friend: friendsOfUTreeSet){
                //якщо серед них буде наш залогінований користувач, то добавляємо в список справжніх друзів його
                // та припиняєм цикл
                if (friend.getId()==user.getId()){
                    friendsWhichAcceptedUserApplication.add(u);
                    break;
                }
            }
        }

        //передаєм на сторінку справжніх друзів
        return friendsWhichAcceptedUserApplication;
    }



    public Set<User> subscribersOfAuthentication(User user, Set<User> friendsWhichAcceptedUserApplication){
        Set<User> subscribers = user.getSubscribers();
        Set<User> subscribersWhichArentFriendsOfUser = new TreeSet<>();
        for (User u: subscribers){
            int count = 0;
            for (User f: friendsWhichAcceptedUserApplication) {
                if (u.getId() != f.getId()) {
                    count++;
                } else break;
            }
            if (count == friendsWhichAcceptedUserApplication.size()){
                subscribersWhichArentFriendsOfUser.add(u);
            }
        }
        return subscribersWhichArentFriendsOfUser;
    }

    public JSONArray fillJsonArrayByComments(List<Comment> comments){
        JSONArray jsonArray = new JSONArray();
        for (Comment comment: comments){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id",comment.getUserFromIdPattern());
            jsonObject.putOnce("text",comment.getText());
            jsonObject.putOnce("userUrlImage",comment.getUserFromNewestUrlImage());
            jsonArray.put(jsonObject);
        }
        return jsonArray;
    }

    public JSONObject createVideoJsonObject(Video video, long authId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("idVideo", video.getId());
        jsonObject.putOnce("nameVideo",video.getName());
        jsonObject.putOnce("urlVideo", video.getUrl());
        /*for background image url in jsp*/
        if (video.getUrlImageBanner() != null)
            jsonObject.putOnce("urlImage", video.getUrlImageBanner());
        else jsonObject.putOnce("urlImage", "/resources/img/icons/videoBannerStandard.png");
            /**/
        return jsonObject;
    }
}

