package com.social_network.ua.controllers;

import com.social_network.ua.entity.*;
import com.social_network.ua.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    @Autowired
    private ImageService imageService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private VideoService videoService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            User user = userService.findOne(Long.parseLong(authentication.getName()));
            return "redirect:/user/"+user.getId();
        } catch (Exception ex){
            return "views-user-login";
        }
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
        long idLong = Long.parseLong(id);
        User user = userService.findOne(idLong);
        /*Set<String> albumSet = new TreeSet<>();
        for(Album a: user.getAlbums()){
            albumSet.add(a.getName());
        }*/
        List<Album> albums = albumService.findAllAlbumsByUser(user);

        albumModel.addAttribute("albums",albums);
        List<User_Images> user_images = null;
        if (album.equals("*")) {
           user_images = imageService.findAllByUser(user);
        } else {
            Album selectedAlbum = albumService.findOneByNameAndUserId(album,idLong);
            user_images = imageService.findAllByAlbum(selectedAlbum);
            model.addAttribute("album",selectedAlbum);
        }
        model.addAttribute("images_all",user_images);
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

    @RequestMapping(value = "/videos",method = RequestMethod.GET)
    public String vidoeAll(Model model){
        model.addAttribute("videoAll",videoService.findAll());
        return "views-base-videos";
    }


    @RequestMapping(value = "/videoProcessLoadingBannerPage/{id}",method = RequestMethod.GET)
    public String loadBannerToVideo(@PathVariable("id")String id, Model model){
        model.addAttribute("idVideo",id);
        return "views-base-loadVideoBanner";
    }

    @RequestMapping(value = "/messages",method = RequestMethod.GET)
    public String getMessages(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        Set<Message> getAllChats = messageService.getAllChatsWithAuthUser(user);
        Set<Message> getLastMessages = new TreeSet<>();
        for (Message m: getAllChats)
        {
            Message message = messageService.findOne(messageService.findLastIdOfMessageBetweenUsers(userService.getUserOfMessage(m.getId()).getId(),userService.getUserToOfMessage(m.getId()).getId()));
            getLastMessages.add(message);
        }
        List<Message> finalMessagesList = new ArrayList<>(getLastMessages.size());
        for (Message m: getLastMessages)
            finalMessagesList.add(m);
        Collections.reverse(finalMessagesList);
        model.addAttribute("messages",finalMessagesList);
        model.addAttribute("userAuth",user);
        return "views-base-messages";
    }
}
