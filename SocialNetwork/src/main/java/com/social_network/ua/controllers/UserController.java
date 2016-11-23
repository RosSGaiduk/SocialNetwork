package com.social_network.ua.controllers;


import com.social_network.ua.entity.User;
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
    public String goLogin(@PathVariable("id")String id,Model model,Model modelForButton){
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
        return "views-user-selected";
    }
}
