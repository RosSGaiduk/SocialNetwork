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
public class BaseController extends BaseMethods{

    @Autowired
    private UserService userService;
    

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home(Model model,Model modelFriends,Model modelSubscribers){
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

        try {
            User user = userService.findOne(Long.parseLong(authentication.getName()));
            //шукаємо друзів даного юзера
            Set<User> friendsWhichAcceptedUserApplication = friendsOfAuthentication(user);
            //шукаємо підписників даного юзера(авторизованого)
            Set<User> subscribersWhichArentFriendsOfUser = subscribersOfAuthentication(user, friendsWhichAcceptedUserApplication);
            //передаєм на сторінку справжніх друзів
            Set<User> friendsOnly3OfThem = new TreeSet<>();
            int i = 0;
            for (User u : friendsWhichAcceptedUserApplication) {
                if (i == 3) break;
                friendsOnly3OfThem.add(u);
                i++;
            }
            modelFriends.addAttribute("friendsOfUser", friendsOnly3OfThem);
            modelSubscribers.addAttribute("subscribersOfUser", subscribersWhichArentFriendsOfUser);
        } catch (Exception ex){
            modelFriends.addAttribute("friendsOfUser", "");
            modelSubscribers.addAttribute("subscribersOfUser", "");
        }

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
        //шукаємо друзів даного юзера
        Set<User> friendsWhichAcceptedUserApplication = friendsOfAuthentication(user);
        //шукаємо підписників даного юзера(авторизованого)
        Set<User> subscribersWhichArentFriendsOfUser = subscribersOfAuthentication(user,friendsWhichAcceptedUserApplication);
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
