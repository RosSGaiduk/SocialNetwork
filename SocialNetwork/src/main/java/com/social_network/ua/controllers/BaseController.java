package com.social_network.ua.controllers;

import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.services.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Rostyslav on 21.11.2016.
 */
@Controller
public class BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home(Model model,Model model1){
        //System.out.println("Hello");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            model.addAttribute("user", userService.findOne(Long.parseLong(authentication.getName())));
            System.out.println(userService.findOne(Long.parseLong(authentication.getName())).getId());
        } catch (Exception e){
            model.addAttribute("user","no user");
        }
        /*try {
            User user = userService.findOne(Long.parseLong(authentication.getName()));
            System.out.println(user.getId());
            //Не знаю для чого наступний алгоритм пошуку максимуму, але без нього не працює, хоч в мене і дерево,
            //яке сортує повідомлення по даті, я не знаю чому не працює просто без цього алгоритму
            Object[] images =  user.getUserImages().toArray();
            User_Images user_image = (User_Images)images[0];
            Date max = user_image.getDateOfImage();
            int index = 0;
            for (int i = 1; i < images.length; i++){
                User_Images user_images = (User_Images)images[i];
                if (user_images.getDateOfImage().compareTo(max)==1){
                    max = user_images.getDateOfImage();
                    index = i;
                }
            }
            User_Images image = (User_Images) images[index];
            System.out.println("Image: "+image.getUrlOfImage());
            //System.out.println(image);
            model1.addAttribute("image", image.getUrlOfImage());
        } catch (Exception e){
            model1.addAttribute("image","");
        }*/
        return "views-base-home";
    }

    @RequestMapping(value = "/messagePage",method = RequestMethod.GET)
    public String messagePage(Model model,Model modelUser){
        model.addAttribute("users",userService.findAll());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));

        model.addAttribute("usersThis",userService.findOne(Long.parseLong(authentication.getName())));
        return "views-test-test";
    }

    @RequestMapping(value = "/friends",method = RequestMethod.GET)
    public String friendSearchPage(Model model,Model modelSubscribers){
        //дістаємо авторизацію, тобто залогінованого користувача
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //відносно авторизації отримуєм користувача з бази даних
        User user = userService.findOne(Long.parseLong(authentication.getName()));
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


        //передаєм на сторінку справжніх друзів
        model.addAttribute("friendsOfUser",friendsWhichAcceptedUserApplication);
        modelSubscribers.addAttribute("subscribersOfUser",subscribersWhichArentFriendsOfUser);
        return "views-base-friends";
    }


    @RequestMapping(value = "/photos",method = RequestMethod.GET)
    public String photosPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long authLong = Long.parseLong(authentication.getName());
        User user = userService.findOne(authLong);
        Set<User_Images> user_images = user.getUserImages();

        model.addAttribute("images_all",user_images);
        System.out.println(user_images.size());
        return "views-user-photos";
    }


    public Set<User> listToSet(List<User>users){
        Set<User> treeOfUsers = new TreeSet<>();
        for (int i = 0; i < users.size(); i++)
            treeOfUsers.add(users.get(i));
        return treeOfUsers;
    }
}
