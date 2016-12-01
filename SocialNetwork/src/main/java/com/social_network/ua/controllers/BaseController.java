package com.social_network.ua.controllers;

import com.social_network.ua.entity.Music;
import com.social_network.ua.entity.Record;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.services.MusicService;
import com.social_network.ua.services.RecordService;
import com.social_network.ua.services.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by Rostyslav on 21.11.2016.
 */
@Controller
public class BaseController extends BaseMethods{

    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private MusicService musicService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home
            (Model model,
             Model modelFriends,
             Model modelSubscribers,
             Model modelRecords,
             Model modelIdUserAuth,
             Model modelForButton,
             Model musicOfAuth
            ){
        //System.out.println("Hello");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user =  userService.findOne(Long.parseLong(authentication.getName()));
            model.addAttribute("user",user);
            if (user.getMusics().size()>0)
                musicOfAuth.addAttribute("musicOfAuth",user.getMusics().get(0));
            else musicOfAuth.addAttribute("musicOfAuth",new Music());
            System.out.println(userService.findOne(Long.parseLong(authentication.getName())).getId());
        } catch (Exception e){
            model.addAttribute("user","no user");
        }

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

            //Set<Record> records = user.getRecordsToUser();

            List<Record> records = recordService.findAllInTheWallOf(Long.parseLong(authentication.getName()));
            List<Record> inverseRecords = new ArrayList<>();

            for (int j = records.size()-1; j >=0; j--)
                inverseRecords.add(records.get(j));

            modelForButton.addAttribute("friendOrNo","hidden");
            modelRecords.addAttribute("records",inverseRecords);
            modelFriends.addAttribute("friendsOfUser", friendsOnly3OfThem);
            modelSubscribers.addAttribute("subscribersOfUser", subscribersWhichArentFriendsOfUser);
        } catch (Exception ex){
            modelFriends.addAttribute("friendsOfUser", "");
            modelSubscribers.addAttribute("subscribersOfUser", "");
        }

        try {
            modelIdUserAuth.addAttribute("userAuth", userService.findOne(Long.parseLong(authentication.getName())));
        } catch (Exception ex){
            modelIdUserAuth.addAttribute("userAuth", "no user auth");
        }
        return "views-user-selected";
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

    @RequestMapping(value = "/friendsOf/{id}",method = RequestMethod.GET)
    public String friendSearchPage(@PathVariable("id")String id, Model model, Model modelSubscribers){
        //відносно авторизації отримуєм користувача з бази даних
        User user = userService.findOne(Long.parseLong(id));
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

    @RequestMapping(value = "/music",method = RequestMethod.GET)
    public String musicAll(Model model){
        model.addAttribute("musicAll",musicService.findAll());
        return "views-base-music";
    }
}
