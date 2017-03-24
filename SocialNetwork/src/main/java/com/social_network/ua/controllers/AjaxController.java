package com.social_network.ua.controllers;


import com.social_network.ua.entity.*;
import com.social_network.ua.enums.RecordType;
import com.social_network.ua.services.*;
import com.social_network.ua.services.implementation.MessagesUpdatorImpl;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private CommunityService communityService;
    @Autowired
    private CommunitySubscriberService communitySubscriberService;
    @Autowired
    private MusicService musicService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;

    @RequestMapping(value = "/testGo",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String testGo(@RequestParam String message){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return message;
    }

    @RequestMapping(value = "/sendMessage",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
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
                    jsonObject.putOnce("minId", messages.get(messages.size()-1).getId());
                    jsonObject.putOnce("maxId", messages.get(0).getId());
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

    @RequestMapping(value = "/update",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
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
    @RequestMapping(value = "/checkIfNewMessages",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
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


    @RequestMapping(value = "/findFriends",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
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

    @RequestMapping(value = "/updateRecords",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
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
        record.setType(RecordType.TEXT.toString());
        recordService.add(record);

        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("userFromImage",user.getNewestImageSrc());
        jsonObject.putOnce("id",record.getId());
        jsonObject.putOnce("text",newRecord);
        jsonObject.putOnce("date",date);


        return jsonObject.toString();
    }

    @RequestMapping(value = "/deleteRecord", method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String deleteRecord(@RequestParam String idRecord){
        long idRec = Long.parseLong(idRecord);
        recordService.delete(idRec);
        return idRecord;
    }

    @RequestMapping(value = "/addMusicToUser",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String addMusicToUser(@RequestParam String idMusic){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long idUserLng = Long.parseLong(authentication.getName());
        long idMusicLng = Long.parseLong(idMusic);
        userService.addMusicToUser(idUserLng,idMusicLng);
        return "";
    }

    @RequestMapping(value = "/addPhotoToAlbum", method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String addPhotoToAlbum(@RequestParam String idPhoto,@RequestParam String nameAlbum){
        User_Images user_images = imageService.findOne(Long.parseLong(idPhoto));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Album album = albumService.findOneByNameAndUserId(nameAlbum, Long.parseLong(authentication.getName())); //норм
        user_images.setAlbum(album);
        imageService.edit(user_images);
        return "Success!";
    }

    @RequestMapping(value = "/checkPhotosFromAlbumOfUser", method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
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


    @RequestMapping(value = "/getPreviousMessages", method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String getPreviousMessages(@RequestParam String userToId,@RequestParam String minId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long authId = Long.parseLong(authentication.getName());
        long userToIdLong = Long.parseLong(userToId);
        long minIdLong = Long.parseLong(minId);
        //System.out.println("Auth: "+authId+", user to: "+userToIdLong+", minIdLong: "+minIdLong);

        JSONArray jsonArray = new JSONArray();
        List<Message> messages = messageService.findAllByIdsAndMinId(authId,userToIdLong,minIdLong);
        //System.out.println("Size of messages: "+messages.size());
        for (int i = 0; i < messages.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id", messages.get(i).getId());
            jsonObject.putOnce("data", messages.get(i).getDateOfMessage());
            jsonObject.putOnce("text", messages.get(i).getText());
            if (userService.getUserOfMessage(messages.get(i).getId()).getId() == authId)
                jsonObject.putOnce("fromUser", true);
            else jsonObject.putOnce("fromUser", false);
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    @RequestMapping(value = "/angularFindEmail",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String findUserByEmail(@RequestParam String email){
        //System.out.println(email);
        JSONObject jsonObject = new JSONObject();
        if (userService.findUserByEmail(email)){
            jsonObject.putOnce("color","orangered");
            jsonObject.putOnce("message","There is user with the same email");
        }
        else {
            jsonObject.putOnce("color","blue");
            jsonObject.putOnce("message","OK");
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/subscribe/{communityId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String subscribe(@RequestParam String userId,@PathVariable("communityId")String communityId){
        String message = "";
        Community community = communityService.findOne(Long.parseLong(communityId));
        Community_Subscriber community_subscriber = communitySubscriberService.findOneByUserIdAndCommunityId(Long.parseLong(userId),Long.parseLong(communityId));
            if (community_subscriber==null) {
                community_subscriber = new Community_Subscriber();
                community_subscriber.setCommunity_id(Long.parseLong(communityId));
                community_subscriber.setSubscriber_id(Long.parseLong(userId));
                communitySubscriberService.add(community_subscriber);
                community.setCountSubscribers(community.getCountSubscribers()+1);
                message = "subscribed|"+community.getCountSubscribers();
            }
            else {
                communitySubscriberService.delete(community_subscriber);
                community.setCountSubscribers(community.getCountSubscribers()-1);
                message = "cancelled|"+community.getCountSubscribers();
            }
        communityService.edit(community);
        return message;
    }

    @RequestMapping(value = "/updateLogosSubscribersOfCommuity/{communityId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String check(@PathVariable("communityId")String communityId){
        //System.out.println("HEEEREEEEEE");
        Community community = communityService.findOne(Long.parseLong(communityId));
        List<User> users = userService.findAllUsersOfCommunity(community,6);
        JSONArray jsonArray = new JSONArray();

        for (User user: users){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id",user.getId());
            jsonObject.putOnce("urlImage",user.getNewestImageSrc());
            jsonObject.putOnce("firstName",user.getFirstName());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }


    @RequestMapping(value = "/showAllSubscribersOfCommunity/{communityId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String showSubscribers(@PathVariable("communityId") String communityId){
        Community community = communityService.findOne(Long.parseLong(communityId));
        List<User> users = userService.findAllUsersOfCommunity(community,Integer.MAX_VALUE);

        JSONArray jsonArray = new JSONArray();
        for (User u: users){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id",u.getId());
            jsonObject.putOnce("firstName",u.getFirstName());
            jsonObject.putOnce("lastName",u.getLastName());
            jsonObject.putOnce("urlImage",u.getNewestImageSrc());
            jsonArray.put(jsonObject);
        }
        //System.out.println("Size of JSON array: "+jsonArray.length());
        return jsonArray.toString();
    }


    @RequestMapping(value = "/showAllMusicOfCommunity/{communityId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String showMusic(@PathVariable("communityId") String communityId){
        List<Music> musics = musicService.findAllByCommunityId(Long.parseLong(communityId),Integer.MAX_VALUE);
        JSONArray jsonArray = new JSONArray();
        for (Music m: musics){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id",m.getId());
            jsonObject.putOnce("nameOfSong",m.getNameOfSong());
            jsonObject.putOnce("urlOfSong",m.getUrlOfSong());
            jsonArray.put(jsonObject);
        }
        //System.out.println("Size of JSON array: "+jsonArray.length());
        return jsonArray.toString();
    }


    @RequestMapping(value = "/leaveComment/{id}",method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String leaveComment(@PathVariable("id")String id,@RequestParam String text){
        //System.out.println("adding a comment, text: "+text+", id: "+id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        User_Images image = imageService.findOne(Long.parseLong(id));
        //System.out.println("User image: id: "+image.getId()+",url: "+image.getUrlOfImage());
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setUserImage(image);
        comment.setUserFromIdPattern(Long.parseLong(authentication.getName()));
        comment.setUserFromNewestUrlImage(user.getNewestImageSrc());
        commentService.add(comment);
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("id",comment.getUserFromIdPattern());
        jsonObject.putOnce("text",comment.getText());
        jsonObject.putOnce("imageSrc",comment.getUserFromNewestUrlImage());
        return jsonObject.toString();
    }

    @RequestMapping(value = "/loadCommentsUnderImage",method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String loadComments(@RequestParam String id){
        //System.out.println("Updating comments "+id);
        User_Images image = imageService.findOne(Long.parseLong(id));
        JSONArray jsonArray = new JSONArray();
        List<Comment> comments = commentService.findAllByImageId(image.getId());
        //System.out.println("Size: "+comments.size());
        for (Comment comment: comments){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id",comment.getUserFromIdPattern());
            jsonObject.putOnce("text",comment.getText());
            jsonObject.putOnce("userUrlImage",comment.getUserFromNewestUrlImage());
            jsonArray.put(jsonObject);
        }
        //System.out.println("Json array length: "+jsonArray.length());
        return jsonArray.toString();
    }

    @RequestMapping(value = "/leaveLike",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String leavelike(@RequestParam String type,@RequestParam String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        switch (type){
            case "image":{
                User_Images user_images = imageService.findOne(Long.parseLong(id));
                LLike likeSearch = likeService.findOneByUserAndImage(user,user_images);
                if (likeSearch==null) {
                    LLike like = new LLike();
                    like.setUser(user);
                    like.setUserImage(user_images);
                    likeService.add(like);
                    user_images.setCountLikes(user_images.getCountLikes()+1);
                    imageService.edit(user_images);
                }
                else {
                    user_images.setCountLikes(user_images.getCountLikes()-1);
                    imageService.edit(user_images);
                    likeService.delete(likeSearch);
                }
            } break;

            case "record":{
                LLike like = new LLike();
                like.setUser(user);
                Record record = recordService.findOne(Long.parseLong(id));
                like.setRecord(record);
                likeService.add(like);
            } break;

            case "comment":{
                LLike like = new LLike();
                like.setUser(user);
                Comment comment = commentService.findOne(Long.parseLong(id));
                like.setComment(comment);
                likeService.add(like);
            } break;
        }
        return "success";
    }

    @RequestMapping(value = "/getCountLikesOfEntity/{id}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String getCountLikesOfEntity(@PathVariable("id") String id,@RequestParam String type){
        //System.out.println("getting likes");
        JSONObject jsonObject = new JSONObject();
        //String returnValue = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        switch (type) {
            case "image":{
                User_Images image = imageService.findOne(Long.parseLong(id));
                //returnValue = ""+image.getCountLikes();
                jsonObject.putOnce("countLikes",image.getCountLikes());
                LLike like = likeService.findOneByUserAndImage(user,image);
                if (like==null) jsonObject.putOnce("liked",false);
                else jsonObject.putOnce("liked",true);
            } break;

            case "comment":{
                //System.out.println("Getting count of likes from Comment");
                //some code here
            } break;

            case "record":{
                //System.out.println("Getting count of likes from Record");
                //some code here
            } break;
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/loadAllRecords",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String loadAllRecordsOfUser(@RequestParam String userId){
        //System.out.println("Loading all records of user: "+userId);
        JSONArray jsonArray = new JSONArray();
        List<Record> records = recordService.findAllInTheWallOf(Long.parseLong(userId));
        for (int i = records.size()-1; i >=0; i--){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id",records.get(i).getId());
            jsonObject.putOnce("date",records.get(i).getDateOfRecord());
            jsonObject.putOnce("url",records.get(i).getUrl());
            if (records.get(i).getText()!=null && records.get(i).getText()!="")
            jsonObject.putOnce("text",records.get(i).getText());
            else jsonObject.putOnce("text","");
            jsonObject.putOnce("urlUserImage",records.get(i).getUrlUserImagePattern());
            jsonObject.putOnce("nameRecord",records.get(i).getNameRecord());
            /*if (records.get(i).isHasImage())
            jsonObject.putOnce("hasImage","true");
            else jsonObject.putOnce("hasImage","false");*/
            jsonObject.putOnce("type",records.get(i).getType());
            jsonArray.put(jsonObject);
        }
        //System.out.println("Size of jsonArray "+jsonArray.length());
        return jsonArray.toString();
    }

    @RequestMapping(value = "/previousAvaOfUser/{userId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String loadAllRecordsOfUser(@PathVariable("userId") String userId,@RequestParam String photoId){
        User_Images user_images = imageService.getPreviousImageFromMainAlbum(Long.parseLong(userId),Long.parseLong(photoId));
        System.out.println("Image found: {id: "+user_images.getId()+"}, url: "+user_images.getUrlOfImage());
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("url",user_images.getUrlOfImage());
        jsonObject.putOnce("id",user_images.getId());
        return jsonObject.toString();
    }

    @RequestMapping(value = "/getUsersThatLikedImageWithLimit/{imageId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String getUsersThatLikedImage(@PathVariable("imageId")String imageId,@RequestParam String limit){
        User_Images user_images = imageService.findOne(Long.parseLong(imageId));
        List<User> usersLiked = userService.getAllUsersThatLikedImageWithLimit(user_images,Integer.parseInt(limit));
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < usersLiked.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("urlImage",usersLiked.get(i).getNewestImageSrc());
            jsonObject.putOnce("id",usersLiked.get(i).getId());
            jsonObject.putOnce("details",usersLiked.get(i).getFirstName());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @RequestMapping(value = "/loadAllUsersWhoLikedImage/{imageId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String loadAllUsersWhoLikedImage(@PathVariable("imageId")String imageId){
        User_Images user_images = imageService.findOne(Long.parseLong(imageId));
        List<User> usersLiked = userService.getAllUsersThatLikedImage(user_images);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < usersLiked.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("urlImage",usersLiked.get(i).getNewestImageSrc());
            jsonObject.putOnce("id",usersLiked.get(i).getId());
            jsonObject.putOnce("name",usersLiked.get(i).getFirstName());
            jsonObject.putOnce("lastName",usersLiked.get(i).getLastName());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }


    @RequestMapping(value = "/updateOnlineUser/{id}",method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String updateLastClickAuthUser(@PathVariable("id")String id,@RequestParam String time,@RequestParam boolean setOnline){
        User user = userService.findOne(Long.parseLong(id));
        System.out.println("boolean: "+setOnline);
        System.out.println("idUser: "+id);
        if (setOnline){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User authUser = userService.findOne(Long.parseLong(authentication.getName()));
            System.out.println("Auth user "+authUser.getId());
            authUser.setOnline(true);
            Date lastOnline = new Date(System.currentTimeMillis());
            String timeToDb  = DateFormatUtils.format(lastOnline, "yyyy/MM/dd HH:mm:ss");
            System.out.println("Time to db: "+timeToDb);
            authUser.setLastOnline(timeToDb);
            userService.edit(authUser);
            if (user.getIsOnline()==true) {
                System.out.println("User with id "+user.getId()+" is online");
                return "Online";
            }
            else {
                System.out.println("User with id "+user.getId()+" isn't online");
                return "Was online: "+user.getLastOnline();
            }
        }
        System.out.println("Time: "+time);
        long timeLong = new Date(user.getLastOnline()).getTime();
        Date date = new Date(System.currentTimeMillis());
        long millis = date.getTime();
        long difference = Math.abs(millis-timeLong);
        System.out.println("Current time: "+millis);
        System.out.println("Difference: "+difference);
        //15 minutes
        if (difference>15*60000){
            user.setOnline(false);
            userService.edit(user);
            return "Was online: "+user.getLastOnline();
        } else {
            user.setOnline(true);
            userService.edit(user);
            return "Online";
        }
    }


    @RequestMapping(value = "/previousImageOfAlbum",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String getPreviousImageOfAlbum(@RequestParam String photoId){
        Album album = albumService.findOneByImageId(Long.parseLong(photoId));
        User_Images image = imageService.getPreviousImageFromAlbum(album,Long.parseLong(photoId));
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("id",image.getId());
        jsonObject.putOnce("url",image.getUrlOfImage());
        return jsonObject.toString();
    }
}
