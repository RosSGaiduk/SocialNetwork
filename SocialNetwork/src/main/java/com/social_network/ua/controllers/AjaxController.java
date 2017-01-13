package com.social_network.ua.controllers;


import com.social_network.ua.entity.*;
import com.social_network.ua.services.*;
import com.social_network.ua.services.implementation.MessagesUpdatorImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Autowired
    private RecordService recordService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private MessagesUpdatorImpl messagesUpdator;
    @Autowired
    private AlbumService albumService;

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

        messagesUpdator.add(new MessagesUpdator(authId,Long.parseLong(userToId)));

        System.out.println(message+" from "+authentication.getName());
        return message;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    public String updateMessagesIn2Methods(long userAuthId,long userToId,int countInt){
        long countBetween2users = messageService.findAllLastBy2ids(userAuthId,userToId);
        int limit = (int)(countBetween2users-countInt);

        JSONArray jsonArray = new JSONArray();

        if (countBetween2users>countInt) {
            List<Message> messages = messageService.findAllByIdsAndCount(userToId,userAuthId,limit);
            System.out.println("Size: "+messages.size());
            for (int i = messages.size() - 1; i >= 0; i--) {
                JSONObject jsonObject = new JSONObject();
                //String from = stringUTF_8Encode(userService.findOne(messages.get(i).getUserFrom().getId()).getFirstName());
                jsonObject.putOnce("data", messages.get(i).getDateOfMessage() + " FROM " + messages.get(i).getUserFrom().getFirstName());
                jsonObject.putOnce("text", messages.get(i).getText());
                if (messages.get(i).getUserFrom().getId() == userAuthId) jsonObject.putOnce("fromUser", true);
                else jsonObject.putOnce("toUser", false);

                //Set<User_Images> userImages = userService.findOne(userToId).getUserImages();
                jsonArray.put(jsonObject);
            }

        }

        //messagesUpdator.deleteWhereUserFromLikeId1AndUserToLikeId2(userToId,userAuthId);
        return jsonArray.toString();
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String updateMessages(@RequestParam String userToId,@RequestParam String count){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long userAuthId = Long.parseLong(authentication.getName());
        long user1 = Long.parseLong(userToId);
        int countInt = Integer.parseInt(count);
        return updateMessagesIn2Methods(user1,userAuthId,countInt);
    }

    //не працює
    @RequestMapping(value = "/checkIfNewMessages",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String checkMessages(@RequestParam String userToId){
        long idUser = Long.parseLong(userToId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long idUserAuth = Long.parseLong(authentication.getName());
        if (messagesUpdator.findMessageBetweenUsers(idUser,idUserAuth)){
            return "true";
        }
        else return "false";
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

    @RequestMapping(value = "/updateRecords",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String updateRecords(@RequestParam String newRecord,
                                @RequestParam String image,
                                @RequestParam String userFrom,
                                @RequestParam String userTo
                                ){
        long idUser = Long.parseLong(userFrom);
        long idUserTo = Long.parseLong(userTo);
        User user = userService.findOne(idUser);
        User userToMess = userService.findOne(idUserTo);

        Record record = new Record();
        record.setText(newRecord);
        Date date = new Date(System.currentTimeMillis());
        record.setDateOfRecord(date);
        record.setUser(userToMess);
        record.setUserFrom(user);

        if (image.equals("")) record.setHasImage(false);
        else record.setHasImage(true);

        recordService.add(record);

        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("userFromImage",user.getNewestImageSrc());
        jsonObject.putOnce("text",newRecord);
        jsonObject.putOnce("date",date);
        System.out.println("Text: "+jsonObject.get("text"));

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
        System.out.println("Length: "+jsonArray.length());
        return jsonArray.toString();
    }

    @RequestMapping(value = "/deleteRecord", method = RequestMethod.GET, produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String deleteRecord(@RequestParam String idRecord){
        long idRec = Long.parseLong(idRecord);
        recordService.delete(idRec);
        return idRecord;
    }

    @RequestMapping(value = "/addMusicToUser",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String addMusicToUser(@RequestParam String idMusic){
        System.out.println("Add music to user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long idUserLng = Long.parseLong(authentication.getName());
        long idMusicLng = Long.parseLong(idMusic);
        userService.addMusicToUser(idUserLng,idMusicLng);
        return "";
    }

    @RequestMapping(value = "/addPhotoToAlbum", method = RequestMethod.GET, produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String addPhotoToAlbum(@RequestParam String idPhoto,@RequestParam String nameAlbum){
        System.out.println("_____________________________________\nId photo: "+idPhoto+" name album selected: "+nameAlbum); //норм
        System.out.println("Album name: "+nameAlbum);
        User_Images user_images = imageService.findOne(Long.parseLong(idPhoto));
        System.out.println(user_images.getId()); //норм
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Album album = albumService.findOneByNameAndUserId(nameAlbum, Long.parseLong(authentication.getName())); //норм
        System.out.println("Album name(found): "+album.getName()+", album id(found): "+album.getId());
        user_images.setAlbum(album);
        imageService.edit(user_images);
        return "Success!";
    }
    @RequestMapping(value = "/checkPhotosFromAlbumOfUser", method = RequestMethod.GET, produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String checkPhotosFromAlbumOfUser(@RequestParam String idUserChecked,@RequestParam String nameAlbum){
        if (nameAlbum.equals("*")){
            System.out.println("*********");
            Set<User_Images> images = userService.findOne(Long.parseLong(idUserChecked)).getUserImages();
            return makeJsonArray(userService.findOne(Long.parseLong(idUserChecked)),images);
        }
        Album album = albumService.findOneByNameAndUserId(nameAlbum, Long.parseLong(idUserChecked));
        return makeJsonArrayWithAlbums(userService.findOne(Long.parseLong(idUserChecked)),album);
    }

    public JSONObject makeJsonPictureAndAlbums(User user,User_Images img) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("url",img.getUrlOfImage());
        jsonObject.putOnce("idOfImg",img.getId());
        if (img.getAlbum() != null)
        jsonObject.putOnce("albumOfPhoto",img.getAlbum().getName());
        else jsonObject.putOnce("albumOfPhoto","");
        Set<String> albumStr = new TreeSet<>();
        for (Album albumOfUser: user.getAlbums()) {
            albumStr.add(albumOfUser.getName());
        }
        jsonObject.putOnce("albums",albumStr);
        return jsonObject;
    }

    public String makeJsonArray(User user,Set<User_Images> images){
        JSONArray jsonArray = new JSONArray();
        for (User_Images img: images){
            JSONObject jsonObject = makeJsonPictureAndAlbums(user,img);
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    public String makeJsonArrayWithAlbums(User user,Album album){
        JSONArray jsonArray = new JSONArray();
        for (User_Images img: album.getUser_images()){
            JSONObject jsonObject = makeJsonPictureAndAlbums(user,img);
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }
}
