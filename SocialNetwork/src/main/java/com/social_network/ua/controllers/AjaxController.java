package com.social_network.ua.controllers;


import com.social_network.ua.entity.Message;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.services.MessageService;
import com.social_network.ua.services.UserService;
import org.json.JSONArray;
import org.json.JSONObject;
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
                jsonObject.putOnce("text", messages.get(i).getText());
                if (messages.get(i).getUserFrom().getId() == user2) jsonObject.putOnce("fromUser", true);
                else jsonObject.putOnce("toUser", false);

                Set<User_Images> userImages = userService.findOne(user1).getUserImages();
               /* try {
                    Object[] images =  userImages.toArray();
                    User_Images user_image = (User_Images)images[0];
                    Date max = user_image.getDateOfImage();
                    int index = 0;
                    for (int j = 1; j < images.length; j++){
                        User_Images user_images = (User_Images)images[j];
                        if (user_images.getDateOfImage().compareTo(max)==1){
                            max = user_images.getDateOfImage();
                            index = j;
                        }
                    }
                    User_Images targetImg = (User_Images)images[index];
                    System.out.println(targetImg.getUrlOfImage());
                    jsonObject.putOnce("image",targetImg.getUrlOfImage());
                } catch (Exception ex){
                    jsonObject.putOnce("image","");
                }*/
                jsonArray.put(jsonObject);
            }
        }
        /*System.out.println("Length: "+jsonArray.length());
        System.out.println("-----------------------------------------------------------------");*/
        return jsonArray.toString();
    }


    @RequestMapping(value = "/findFriends",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String findFriends(@RequestParam String friend){
        List<User> users = userService.findAll();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        JSONArray jsonArray = new JSONArray();

        for (User u: users)
            if (u.getFirstName().contains(friend) || u.getLastName().contains(friend)){
                if (u.getId()!=Long.parseLong(authentication.getName())) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOnce("id", u.getId());
                    jsonObject.putOnce("name", u.getFirstName());
                    jsonObject.putOnce("lastName", u.getLastName());
                    jsonObject.putOnce("image", u.getNewestImageSrc());
                    jsonArray.put(jsonObject);
                }
            }


        return jsonArray.toString();
    }


    @RequestMapping(value = "/addUserToFriendsZone",method = RequestMethod.GET)
    @ResponseBody
    public String addUserToFriendZone(@RequestParam String userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //User user = userService.selectUser(Long.parseLong(authentication.getName()),Long.parseLong(userId));
        User user = userService.findOne(Long.parseLong(userId));
        Set<User> subscribersOfUser = user.getSubscribers();
        boolean was = false;
        for (User u:subscribersOfUser) {
            if (was) break;
            if (u.getId()==Long.parseLong(authentication.getName())){
                was = true;
                break;
            }
        }

        if (!was)
        userService.addFriendToUser(Long.parseLong(authentication.getName()),Long.parseLong(userId));
        return "views-base-home";
    }
}
