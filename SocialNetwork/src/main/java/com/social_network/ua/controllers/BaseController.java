package com.social_network.ua.controllers;

import com.social_network.ua.entity.*;
import com.social_network.ua.services.AlbumService;
import com.social_network.ua.services.MusicService;
import com.social_network.ua.services.RecordService;
import com.social_network.ua.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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
    @Autowired
    private AlbumService albumService;

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


    @RequestMapping(value = "/friendsOf/{id}",method = RequestMethod.GET)
    public String friendSearchPage(@PathVariable("id")String id, Model userOf, Model model, Model modelSubscribers,Model anotherPeopleModel){
        //відносно авторизації отримуєм користувача з бази даних
        User user = userService.findOne(Long.parseLong(id));
        //шукаємо друзів даного юзера
        Set<User> friendsWhichAcceptedUserApplication = friendsOfAuthentication(user);
        //шукаємо підписників даного юзера(авторизованого)
        Set<User> subscribersWhichArentFriendsOfUser = subscribersOfAuthentication(user,friendsWhichAcceptedUserApplication);
        //передаєм на сторінку справжніх друзів
        model.addAttribute("friendsOfUser",friendsWhichAcceptedUserApplication);
        modelSubscribers.addAttribute("subscribersOfUser",subscribersWhichArentFriendsOfUser);
        List<Long> idsOfSubscribers = userService.findAllIdsOfSubscribersOfUser(Long.parseLong(id));
        try {
            Long[] anotherIntPeople = new Long[idsOfSubscribers.size()];
            for (int i = 0; i < idsOfSubscribers.size(); i++) {
                anotherIntPeople[i] = idsOfSubscribers.get(i);
            }
            anotherPeopleModel.addAttribute("anotherPeople", userService.findAllThatArentFriendsOfUserAndArentSubscribersOfUser(anotherIntPeople));
        } catch (Exception ex){
            anotherPeopleModel.addAttribute("anotherPeople",userService.findAll());
        }
        userOf.addAttribute("userThis",id);
        return "views-base-friends";
    }

    @RequestMapping(value = "/photosOf/{id}/{album}",method = RequestMethod.GET)
    public String photosOfUser(@PathVariable("id") String id, @PathVariable("album") String album,Model model,Model albumModel,Model userModel,Model userAuthModel){
        System.out.println("Selected album: "+album);
        long idLong = Long.parseLong(id);
        User user = userService.findOne(idLong);
        Set<String> albumSet = new TreeSet<>();
        for(Album a: user.getAlbums()){
            albumSet.add(a.getName());
        }
        albumModel.addAttribute("albums",albumSet);
        Set<User_Images> user_images = null;
        if (album.equals("*")) {
           user_images = user.getUserImages();
        } else {
            Album selectedAlbum = albumService.findOneByNameAndUserId(album,idLong);
            user_images = selectedAlbum.getUser_images();
        }
        model.addAttribute("images_all",user_images);
        System.out.println(user_images.size());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userModel.addAttribute("userPageId",user.getId());
        userAuthModel.addAttribute("userAuthId",authentication.getName());
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
