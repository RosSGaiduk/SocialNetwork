package com.social_network.ua.controllers;


import com.social_network.ua.entity.*;
import com.social_network.ua.services.*;
import com.social_network.ua.services.implementation.MessagesUpdatorImpl;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private MessagesUpdatorImpl messagesUpdatorService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private SubscriberService subscriberService;

    @RequestMapping(value = "/testGo",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String testGo(@RequestParam String message){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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

        newMessage.setDateOfMessage(new Date(System.currentTimeMillis()));
        messageService.add(newMessage);

        MessagesUpdator messagesUpdator = messagesUpdatorService.findOneBy2Ids(authId,Long.parseLong(userToId));
        if (messagesUpdator == null) {
            messagesUpdatorService.add(new MessagesUpdator(authId, Long.parseLong(userToId)));
        }
        else {
            messagesUpdator.setCountMessages(messagesUpdator.getCountMessages()+1);
            messagesUpdatorService.edit(messagesUpdator);
        }
        return message;
    }
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
    public JSONArray updateMessagesIn2Methods(long userAuthId,long userToId,long maxId){
        //можна зекономити тут, просто не дописувати в messageUpdator ше 1 поле, а доробити змінну countMessages,
        //і просто збільшувати її на 1 між даними 2 користувачами, тоді, запит робитиметься одразу

        JSONArray jsonArray = new JSONArray();

        long maxIdFromDb = messageService.findLastIdOfMessageBetweenUsers(userToId,userAuthId);
            if (maxIdFromDb>maxId) {
                List<Message> messages = messageService.findAllByIdsAndMaxId(userToId, userAuthId, maxId);

                for (int i = messages.size() - 1; i >= 0; i--) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.putOnce("id", messages.get(i).getId());
                    jsonObject.putOnce("data", messages.get(i).getDateOfMessage());
                    jsonObject.putOnce("text", messages.get(i).getText());
                    if (userService.getUserOfMessage(messages.get(i).getId()).getId() == userAuthId)
                        jsonObject.putOnce("fromUser", true);
                    else jsonObject.putOnce("fromUser", false);
                    jsonArray.put(jsonObject);
                }
            }
        return jsonArray;
    }

    @RequestMapping(value = "/update",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String updateMessages(@RequestParam String userToId,@RequestParam String maxId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long userAuthId = Long.parseLong(authentication.getName());
        long user1 = Long.parseLong(userToId);
        long max = Long.parseLong(maxId);
        JSONArray jsonArray = updateMessagesIn2Methods(user1,userAuthId,max);
        return jsonArray.toString();
    }

    //не працює
    @RequestMapping(value = "/checkIfNewMessages",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String checkMessages(@RequestParam String userToId){
        long idUser = Long.parseLong(userToId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long idUserAuth = Long.parseLong(authentication.getName());
        if (messagesUpdatorService.findMessageBetweenUsers(idUser,idUserAuth)){
            return "true";
        }
        else return "false";
    }


    @RequestMapping(value = "/findFriends",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String findFriends(@RequestParam String friend,@RequestParam String user){
        List<User> users = userService.findAllByInput(friend);
        JSONArray jsonArray = new JSONArray();

        for (User u: users) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id", u.getId());
            jsonObject.putOnce("name", u.getFirstName());
            jsonObject.putOnce("lastName", u.getLastName());
            jsonObject.putOnce("image", u.getNewestImageSrc());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @RequestMapping(value = "/addUserToFriendsZone",method = RequestMethod.GET)
    @ResponseBody
    public String addUserToFriendZone(@RequestParam String userId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
        if (!was) {
            subscribersCopy subscriber = new subscribersCopy();
            subscriber.setUser_id(Long.parseLong(authentication.getName()));
            subscriber.setSubscriber_id(Long.parseLong(userId));
            subscriberService.add(subscriber);
            userService.addFriendToUser(Long.parseLong(authentication.getName()), Long.parseLong(userId));
        }
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

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonObject);
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long idUserLng = Long.parseLong(authentication.getName());
        long idMusicLng = Long.parseLong(idMusic);
        userService.addMusicToUser(idUserLng,idMusicLng);
        return "";
    }

    @RequestMapping(value = "/addPhotoToAlbum", method = RequestMethod.GET, produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String addPhotoToAlbum(@RequestParam String idPhoto,@RequestParam String nameAlbum){
        User_Images user_images = imageService.findOne(Long.parseLong(idPhoto));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Album album = albumService.findOneByNameAndUserId(nameAlbum, Long.parseLong(authentication.getName())); //норм
        user_images.setAlbum(album);
        imageService.edit(user_images);
        return "Success!";
    }

    @RequestMapping(value = "/checkPhotosFromAlbumOfUser", method = RequestMethod.GET, produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String checkPhotosFromAlbumOfUser(@RequestParam String idUserChecked,@RequestParam String nameAlbum){
        if (nameAlbum.equals("*")){
            Set<User_Images> images = userService.findOne(Long.parseLong(idUserChecked)).getUserImages();
            return makeJsonArray(userService.findOne(Long.parseLong(idUserChecked)),images);
        }
        Album album = albumService.findOneByNameAndUserId(nameAlbum, Long.parseLong(idUserChecked));
        return makeJsonArrayWithAlbums(userService.findOne(Long.parseLong(idUserChecked)),album);
    }

    @RequestMapping(value = "/exitUser", method = RequestMethod.GET,produces = {"text/html; charset/UTF-8"})
    @ResponseBody
    public String exitUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        user.setIsOnline(false);
        /*DateFormat dateFormat = new SimpleDateFormat("MM/dd");
        Date date = new Date();
        dateFormat.format(date);
        */
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.now();
        String date = dtf.format(localDate);
        user.setLastOnline(date);
        userService.edit(user);
        return "success";
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
