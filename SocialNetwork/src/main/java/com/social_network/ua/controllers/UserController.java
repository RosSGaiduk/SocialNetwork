package com.social_network.ua.controllers;


import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Rostyslav on 21.10.2016.
 */
@Controller
public class UserController extends BaseMethods{
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/addUser",method = RequestMethod.GET)
    public String addUserPage(Model model){
        model.addAttribute("newUser",new User());
        return "views-user-new";
    }

    @RequestMapping(value = "/createUser",method = RequestMethod.POST)
    public String createUser(@ModelAttribute("newUser")User newUser,
                             @RequestParam("birthDateUser")String dateCalendar
    ){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = formatter.parse(dateCalendar);
            newUser.setBirthDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newUser.setFirstName(stringUTF_8Encode(newUser.getFirstName()));
        newUser.setLastName(stringUTF_8Encode(newUser.getLastName()));

        /*newUser.setFirstName(newUser.getFirstName());
        newUser.setLastName(newUser.getLastName());*/

        /*userValidator.validate(newUser,bindingResult);*/
       /* if (bindingResult.hasErrors()){
            return "views-user-new";
        }*/

        userService.add(newUser);
        return "redirect:/";
    }

    @RequestMapping(value = "/userLogin",method = RequestMethod.GET)
    public String goLogin(Model model){
        model.addAttribute("user",new User());
        return "views-user-login";
    }


    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String goLogin(@PathVariable("id")String id,Model model,Model modelForButton,Model modelFriends,Model modelSubscribers){
        model.addAttribute("user",userService.findOne(Long.parseLong(id)));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<User> subscribersOfUser = userService.findOne(Long.parseLong(id)).getSubscribers();
        boolean wasSubscriber = false;
        for (User u: subscribersOfUser) {
            if (wasSubscriber) break;
            if (u.getId() == Long.parseLong(authentication.getName())) {
                wasSubscriber = true;
                break;
            }
        }
        if (wasSubscriber)
        modelForButton.addAttribute("friendOrNo","hidden");
        else
        modelForButton.addAttribute("friendOrNo","visible");

        try {
            User user = userService.findOne(Long.parseLong(id));
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

        /*try{
        Object[] images =  userService.findOne(Long.parseLong(id)).getUserImages().toArray();
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
        imageUserModel.addAttribute("image",image.getUrlOfImage());
        } catch (Exception ex) {
            imageUserModel.addAttribute("image","");
        }*/

        return "views-user-selected";
    }

    @RequestMapping(value = "/photosOf/{id}",method = RequestMethod.GET)
    public String photosOfUser(@PathVariable("id") String id,Model model){
        long idLong = Long.parseLong(id);
        User user = userService.findOne(idLong);
        Set<User_Images> user_images = user.getUserImages();
        model.addAttribute("images_all",user_images);
        System.out.println(user_images.size());
        return "views-user-photos";
    }

    @RequestMapping(value = "/messagesWithUser/{id}",method = RequestMethod.GET)
    public String messagesWithUser(@PathVariable("id")String id,Model model,Model model1){
        long idLong = Long.parseLong(id);
        model.addAttribute("idOfUser",idLong);
        model1.addAttribute("users",userService.findAll());
        return "views-test-test";
    }

}
