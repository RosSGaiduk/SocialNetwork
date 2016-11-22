package com.social_network.ua.controllers;

import com.social_network.ua.entity.User;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rostyslav on 21.11.2016.
 */
@Controller
public class BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home(){
        return "views-base-home";
    }

    @RequestMapping(value = "/messagePage",method = RequestMethod.GET)
    public String messagePage(Model model){
        model.addAttribute("users",userService.findAll());
        return "views-test-test";
    }

    @RequestMapping(value = "/friends",method = RequestMethod.GET)
    public String friendSearchPage(Model model){
        //дістаємо авторизацію, тобто залогінованого користувача
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //відносно авторизації отримуєм користувача з бази даних
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        //формуємо список всіх користувачів, на які підписався даний користувач(авторизований користувач)
        //ці користувачі МОЖУТЬ бути його друзями, а можуть зберігати його в підписниках
        List<User> usersOfThis = user.getFriends();
        //нам потрібно передати на сторінку всіх друзів даного користувача.
        //якщо ми просто передамо user.getFriends(); на сторінку, то серед цих "друзів" можуть трапитись
        //ті, на які даний користувач підписався, але вони заявку в друзі не прийняли.
        //бо в мене схема добавлення в друзі така: якщо user1 добавляє user2 в друзі, то user2 автоматично стає
        //другом user1, але user1 не буде другом user2, а тільки підписником. Якщо ж user2 так само після того
        //добавить в друзі user1, лише тоді вони будуть друзями між собою. Тому мені потрібно запрограмувати
        //передачу на сторінку юзерів, які по справжньому є друзями даного користувача.

        //в цей список будуть збережені такі юзери
        List<User> friendsWhichAcceptedUserApplication = new ArrayList<>();
        //спочатку пробігаємся циклом по всіх користувачах з тих, на які підписався даний користувач(залогінований)
        for (User u: usersOfThis){
            //з кожного з них дістаєм ДРУЗІВ...
            List<User> friendsOfU = u.getFriends();
            //перебираємо їх
            for (int i = 0; i < friendsOfU.size(); i++){
                //якщо серед них буде наш залогінований користувач, то добавляємо в список справжніх друзів його
                // та припиняєм цикл
                if (friendsOfU.get(i).getId()==user.getId()){
                    friendsWhichAcceptedUserApplication.add(u);
                    break;
                }
            }
        }
        //передаєм на сторінку справжніх друзів
        model.addAttribute("friendsOfUser",friendsWhichAcceptedUserApplication);
        return "views-base-friends";
    }
}
