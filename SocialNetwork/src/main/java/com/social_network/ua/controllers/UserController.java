package com.social_network.ua.controllers;


import com.social_network.ua.entity.*;
import com.social_network.ua.services.RecordService;
import com.social_network.ua.services.UserService;
import com.social_network.ua.validations.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Rostyslav on 21.10.2016.
 */
@Controller
public class UserController extends BaseMethods{
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/addUser",method = RequestMethod.GET)
    public String addUserPage(Model model){
        model.addAttribute("newUser",new User());
        return "views-user-new";
    }

    @RequestMapping(value = "/createUser",method = RequestMethod.POST)
    public String createUser(@ModelAttribute("newUser")User newUser,
                             @RequestParam("birthDateUser")String dateCalendar,
                             BindingResult bindingResult
    ){
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        newUser.setFirstName(stringUTF_8Encode(newUser.getFirstName()));
        newUser.setLastName(stringUTF_8Encode(newUser.getLastName()));

        /*newUser.setFirstName(newUser.getFirstName());
        newUser.setLastName(newUser.getLastName());*/

        /*userValidator.validate(newUser,bindingResult);*/
        /* if (bindingResult.hasErrors()){
            return "views-user-new";
        }*/
        try {
            Date date = formatter.parse(dateCalendar);
            newUser.setBirthDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
            bindingResult.rejectValue("birthDate","birthDate.empty");
        }

        userValidator.validate(newUser,bindingResult);
        if (bindingResult.hasErrors()){
            return "views-user-new";
        }



        userService.add(newUser);
        return "redirect:/";
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public String goLogin(@PathVariable("id")String id,
                          Model model,
                          Model modelForButton,
                          Model modelFriends,
                          Model modelSubscribers,
                          Model modelIdUserAuth,
                          Model modelRecords,
                          Model musicOfAuth
    ){
        User userSearched = userService.findOne(Long.parseLong(id));
        model.addAttribute("user",userSearched);

        //if (userSearched.getMusics().size()>0)
            //musicOfAuth.addAttribute("musicOfAuth",userSearched.getMusics().get(0));

        try {
            musicOfAuth.addAttribute("musicOfAuth", userService.get3LastMusicOfUser(userSearched.getId()));
        } catch (Exception ex) {
            musicOfAuth.addAttribute("musicOfAuth", null);
        }

        //else musicOfAuth.addAttribute("musicOfAuth",new Music());
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
        else {
            if (!(id.equals(authentication.getName())))
                modelForButton.addAttribute("friendOrNo", "visible");
            else  modelForButton.addAttribute("friendOrNo","hidden");
        }

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

        //Set<Record> records = userService.findOne(Long.parseLong(id)).getRecordsToUser();
        List<Record> records = recordService.findAllInTheWallOf(Long.parseLong(id));
        List<Record> inverseRecords = new ArrayList<>();
        for (int i = records.size()-1; i >=0; i--)
            inverseRecords.add(records.get(i));

        modelRecords.addAttribute("records",inverseRecords);
        modelIdUserAuth.addAttribute("userAuth", userService.findOne(Long.parseLong(authentication.getName())));
        return "views-user-selected";
    }

    @RequestMapping(value = "/messagesWithUser/{id}",method = RequestMethod.GET)
    public String messagesWithUser(@PathVariable("id")String id,Model model,Model model1){
        long idLong = Long.parseLong(id);
        model.addAttribute("idOfUser",idLong);
        model1.addAttribute("users",userService.findAll());
        return "views-test-test";
    }

    @RequestMapping(value = "/musicOf/{id}",method = RequestMethod.GET)
    public String musicOfUser(@PathVariable("id")String id,Model modelMusic,Model modelIdAuth,Model modeliIdUserSelected){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(id));
        modelMusic.addAttribute("musicAll",userService.getAllMusicOfUser(Long.parseLong(id)));
        modelIdAuth.addAttribute("idAuth",authentication.getName());
        modeliIdUserSelected.addAttribute("idUserSelected",id);
        return "views-user-music";
    }
}
