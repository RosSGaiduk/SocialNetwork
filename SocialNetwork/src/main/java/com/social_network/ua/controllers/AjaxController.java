package com.social_network.ua.controllers;


import com.social_network.ua.entity.Message;
import com.social_network.ua.services.MessageService;
import com.social_network.ua.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Rostyslav on 01.11.2016.
 */
@Controller
public class AjaxController extends BaseMethods {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;


    @RequestMapping(value = "/testGo",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String testGo(@RequestParam String message){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(message+" from "+authentication.getName());
        return message;
    }

    @RequestMapping(value = "/sendMessage",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String sendMessage(@RequestParam String message,@RequestParam String userToId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long authId = Long.parseLong(authentication.getName());

        Message newMessage = new Message();
        newMessage.setUserFrom(userService.findOne(authId));
        newMessage.setUserTo(userService.findOne(Long.parseLong(userToId)));

        newMessage.setText(message);
        //newMessage.setText(stringUTF_8Encode(message));
        //System.out.println(stringUTF_8Encode(message));

        newMessage.setDateOfMessage(new Date(System.currentTimeMillis()));
        messageService.add(newMessage);

        System.out.println(message+" from "+authentication.getName());
        return message;
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String updateMessages(@RequestParam String userToId,@RequestParam String count){
        //System.out.println(userToId);
        //System.out.println("Count: "+count);
        //System.out.println("-----------------------------------------------------------------");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        long user1 = Long.parseLong(userToId);
        long user2 = Long.parseLong(authentication.getName());
        int countInt = Integer.parseInt(count);


        long countBetween2users = messageService.findAllLastBy2ids(user1,user2);
        //System.out.println("count between them in bd: "+countBetween2users);

        int limit = (int)(countBetween2users-countInt);


        JSONArray jsonArray = new JSONArray();

        if (countBetween2users>countInt) {
            List<Message> messages = messageService.findAllByIdsAndCount(user1,user2,limit);
            System.out.println("Size: "+messages.size());
            for (int i = messages.size() - 1; i >= 0; i--) {
                JSONObject jsonObject = new JSONObject();
                //String from = stringUTF_8Encode(userService.findOne(messages.get(i).getUserFrom().getId()).getFirstName());
                jsonObject.putOnce("data", messages.get(i).getDateOfMessage() + " FROM " + messages.get(i).getUserFrom().getFirstName());
                jsonObject.put("text", messages.get(i).getText());
                if (messages.get(i).getUserFrom().getId() == user2) jsonObject.putOnce("fromUser", true);
                else jsonObject.putOnce("toUser", false);
                jsonArray.put(jsonObject);
            }
        }
        /*System.out.println("Length: "+jsonArray.length());
        System.out.println("-----------------------------------------------------------------");*/
        return jsonArray.toString();
    }
}
