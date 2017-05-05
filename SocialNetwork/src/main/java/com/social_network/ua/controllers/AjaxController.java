package com.social_network.ua.controllers;


import com.social_network.ua.entity.*;
import com.social_network.ua.enums.AlbumName;
import com.social_network.ua.enums.RecordType;
import com.social_network.ua.services.*;
import com.social_network.ua.services.implementation.MessagesUpdatorImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Autowired
    private VideoService videoService;


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
                    jsonObject.putOnce("url", messages.get(i).getUrlOfItem()!=null?messages.get(i).getUrlOfItem():"");
                    jsonObject.putOnce("type", messages.get(i).getType()!=null?messages.get(i).getType():"");
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
        System.out.println("delete "+idRecord);
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
        Album albumOfImage = albumService.findOneByImageId(user_images.getId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Album album = albumService.findOneByNameAndUserId(nameAlbum, Long.parseLong(authentication.getName())); //норм
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        //якщо ми переносимо найновішу аву в інший альбом
        if (user.getNewestImageId()==user_images.getId() && albumOfImage.getName().equals(AlbumName.MY_PAGE_PHOTOS)){
            User_Images newMainImage = imageService.getPreviousImageFromMainAlbum(user.getId(),user_images.getId());
            if (newMainImage!=null) {
                user.setNewestImageId(newMainImage.getId());
                user.setNewestImageSrc(newMainImage.getUrlOfImage());
            } else {
                user.setNewestImageSrc("/resources/img/icons/image.png");
                user.setNewestImageId(0);
            }
            userService.edit(user);
            messageService.updateMessagesImageOfUser(user,user.getNewestImageSrc());
            recordService.updateUserImageSrcOfRecords(user);
            commentService.updateCommentsNewestImageSrcOfUser(user);
        }
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

        JSONArray jsonArray = new JSONArray();
        List<Message> messages = messageService.findAllByIdsAndMinId(authId,userToIdLong,minIdLong);
        for (int i = 0; i < messages.size(); i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id", messages.get(i).getId());
            jsonObject.putOnce("data", messages.get(i).getDateOfMessage());
            jsonObject.putOnce("text", messages.get(i).getText());
            if (userService.getUserOfMessage(messages.get(i).getId()).getId() == authId)
                jsonObject.putOnce("fromUser", true);
            else jsonObject.putOnce("fromUser", false);
            jsonObject.putOnce("url", messages.get(i).getUrlOfItem()!=null?messages.get(i).getUrlOfItem():"");
            jsonObject.putOnce("type", messages.get(i).getType()!=null?messages.get(i).getType():"");
            jsonArray.put(jsonObject);
        }

        return jsonArray.toString();
    }

    @RequestMapping(value = "/angularFindEmail",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String findUserByEmail(@RequestParam String email){
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

    @RequestMapping(value = "/findVideos",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String findVideosByInput(@RequestParam String text){
        System.out.println("Text of video: "+text);
        JSONArray jsonArray = new JSONArray();
        List<Video> sameVideo = videoService.findAllByInput(text);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (int i = 0; i < sameVideo.size(); i++){
            JSONObject jsonObject = createVideoJsonObject(sameVideo.get(i),Long.parseLong(authentication.getName()));
            jsonObject.putOnce("myVideo", videoService.videoBelongsToUser(sameVideo.get(i).getId(), Long.parseLong(authentication.getName())));
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @RequestMapping(value = "/findVideosLikeOpened",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String findVideosLikeOpened(@RequestParam String text,@RequestParam String videoId){
        System.out.println("Text of video: "+text);
        JSONArray jsonArray = new JSONArray();
        List<Video> sameVideo = videoService.findAllByInput(text);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (int i = 0; i < sameVideo.size(); i++){
            if (sameVideo.get(i).getId()!=Long.parseLong(videoId)) {
                JSONObject jsonObject = createVideoJsonObject(sameVideo.get(i),Long.parseLong(authentication.getName()));
                jsonObject.putOnce("myVideo", videoService.videoBelongsToUser(sameVideo.get(i).getId(), Long.parseLong(authentication.getName())));
                jsonArray.put(jsonObject);
            }
        }
        return jsonArray.toString();
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
        return jsonArray.toString();
    }


    @RequestMapping(value = "/leaveComment/{id}",method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String leaveComment(@PathVariable("id")String id,@RequestParam String text){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        User_Images image = imageService.findOne(Long.parseLong(id));
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

    @RequestMapping(value = "/leaveCommentUnderVideo/{id}",method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String leaveCommentUnderVideo(@PathVariable("id")String id,@RequestParam String text){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        Video video = videoService.findOne(Long.parseLong(id));
        Comment comment = new Comment();
        comment.setText(text);
        comment.setUser(user);
        comment.setVideo(video);
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
        User_Images image = imageService.findOne(Long.parseLong(id));
        List<Comment> comments = commentService.findAllByImageId(image.getId());
        JSONArray jsonArray = fillJsonArrayByComments(comments);
        return jsonArray.toString();
    }

    @RequestMapping(value = "/loadCommentsUnderVideo",method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String loadCommentsUnderVideo(@RequestParam String id){
        List<Comment> comments = commentService.findAllByVideoId(Long.parseLong(id));
        JSONArray jsonArray = fillJsonArrayByComments(comments);
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
        JSONObject jsonObject = new JSONObject();
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
                //some code here
            } break;

            case "record":{
                //some code here
            } break;
        }
        return jsonObject.toString();
    }

    @RequestMapping(value = "/loadAllRecords",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String loadAllRecordsOfUser(@RequestParam String userId){
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
        return jsonArray.toString();
    }

    @RequestMapping(value = "/previousAvaOfUser/{userId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String loadAllRecordsOfUser(@PathVariable("userId") String userId,@RequestParam String photoId){
        User_Images user_images = imageService.getPreviousImageFromMainAlbum(Long.parseLong(userId),Long.parseLong(photoId));
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("url",user_images.getUrlOfImage());
        jsonObject.putOnce("id",user_images.getId());
        return jsonObject.toString();
    }


    @RequestMapping(value = "/nextAvaOfUser/{userId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String getNextImageOfAlbum(@PathVariable("userId") String userId,@RequestParam String photoId){
        User_Images user_images = imageService.getNextImageFromMainAlbum(Long.parseLong(userId),Long.parseLong(photoId));
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
        JSONArray jsonArray = fillArrayWithUsers(usersLiked);
        return jsonArray.toString();
    }


    @RequestMapping(value = "/loadAllUsersWhoLikedRecord/{recordId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String loadAllUsersWhoLikedRecord(@PathVariable("recordId")String imageId){
        Record record = recordService.findOne(Long.parseLong(imageId));
        List<User> usersLiked = userService.selectAllUsersWhoLikedRecord(record);
        JSONArray jsonArray = fillArrayWithUsers(usersLiked);
        return jsonArray.toString();
    }

    @RequestMapping(value = "/updateOnlineUser/{id}",method = RequestMethod.GET, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String updateLastClickAuthUser(@PathVariable("id")String id,@RequestParam String time,@RequestParam boolean setOnline){
        User user = userService.findOne(Long.parseLong(id));
        if (setOnline){
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User authUser = userService.findOne(Long.parseLong(authentication.getName()));
            authUser.setOnline(true);
            Date lastOnline = new Date(System.currentTimeMillis());
            String timeToDb  = DateFormatUtils.format(lastOnline, "yyyy/MM/dd HH:mm:ss");
            authUser.setLastOnline(timeToDb);
            userService.edit(authUser);
            if (user.getIsOnline()==true) {
                return "Online";
            }
            else {
                return "Was online: "+user.getLastOnline();
            }
        }
        long timeLong = new Date(user.getLastOnline()).getTime();
        Date date = new Date(System.currentTimeMillis());
        long millis = date.getTime();
        long difference = Math.abs(millis-timeLong);
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


    @RequestMapping(value = "/getWidth_HeightAndRatioOfPhoto/{id}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String getDatailsOfPhoto(@PathVariable("id") String id){
        User_Images image = imageService.findOne(Long.parseLong(id));
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("width",image.getWidth());
        jsonObject.putOnce("height",image.getHeight());
        jsonObject.putOnce("ratio",image.getRatio());
        return jsonObject.toString();
    }

    @RequestMapping(value = "/addVideoToUser/{id}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String addVideoToUser(@PathVariable("id")String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        System.out.println("User "+user+", idVideo "+id);
        Video video = videoService.findOne(Long.parseLong(id));
        videoService.addVideoToUser(video,user);
        return "";
    }

    @RequestMapping(value = "/deleteVideoFromUserPage/{id}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String deleteVideoFromMyPage(@PathVariable("id")String idVideo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        videoService.deleteVideoFromUser(Long.parseLong(idVideo),Long.parseLong(authentication.getName()));
        return "Success";
    }

    @RequestMapping(value = "/checkingIfVideoBelongsToAuthUser/{id}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String checkingIfVideoBelongsToAuthUser(@PathVariable("id")String idVideo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Boolean belongs = videoService.videoBelongsToUser(Long.parseLong(idVideo),Long.parseLong(authentication.getName()));
        return belongs.toString();
    }

    @RequestMapping(value = "/loadCountLikesUnderVideo/{id}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String loadCountLikesUnderVideo(@PathVariable("id")String idVideo){
        Video video = videoService.findOne(Long.parseLong(idVideo));
        return video.getCountLikes()+"";
    }


    @RequestMapping(value = "/leaveLikeUnderVideo/{id}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String leaveLikeUnderVideo(@PathVariable("id")String idVideo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Video video = videoService.findOne(Long.parseLong(idVideo));
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        LLike lLike = likeService.findOneByVideoAndUser(video,user);
        JSONObject jsonObject = new JSONObject();
        if (lLike == null){
            jsonObject.putOnce("liked",true);
            video.setCountLikes(video.getCountLikes()+1);
            lLike = new LLike();
            lLike.setUser(user);
            lLike.setVideo(video);
            likeService.add(lLike);
        } else {
            jsonObject.putOnce("liked",false);
            video.setCountLikes(video.getCountLikes()-1);
            likeService.delete(lLike);
        }
        videoService.edit(video);
        System.out.println("Count likes "+video.getCountLikes());
        jsonObject.putOnce("countLikes",video.getCountLikes());
        return jsonObject.toString();
    }

    @RequestMapping(value = "/checkIfUserLikedVideo/{id}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String checkIfUserLikedVideo(@PathVariable("id")String idVideo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Video video = videoService.findOne(Long.parseLong(idVideo));
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        LLike lLike = likeService.findOneByVideoAndUser(video,user);
        if (lLike == null){
            return "false";
        } else return "true";
    }

    @RequestMapping(value = "/leaveLikeUnderRecord/{idRecord}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String leaveLikeUnderRecord(@PathVariable("idRecord")String idRecord){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        Record record = recordService.findOne(Long.parseLong(idRecord));
        LLike like = likeService.userLikedRecord(user,record);
        //System.out.println("Like == null: "+(like==null));
        boolean liked = false;
        if (like == null){
            liked = true;
            like = new LLike();
            like.setUser(user);
            like.setRecord(record);
            record.setCountLikes(record.getCountLikes()+1);
            recordService.edit(record);
            likeService.add(like);
        } else {
            liked = false;
            record.setCountLikes(record.getCountLikes()-1);
            recordService.edit(record);
            likeService.delete(like);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.putOnce("countLikes",record.getCountLikes());
        jsonObject.putOnce("liked",liked);
        jsonObject.putOnce("type",record.getType());
        jsonObject.putOnce("text",record.getText()!=null?record.getText():"");
        jsonObject.putOnce("url",record.getUrl()!=null?record.getUrl():"");
        jsonObject.putOnce("name",record.getNameRecord()!=null?record.getNameRecord():"");
        return jsonObject.toString();
    }

    @RequestMapping(value = "/showUsersWhoLikedCurrentRecordWithLimit", method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String getUsersWhoLikedRecord(@RequestParam String recordId){
        System.out.println("RecordId: "+recordId);
        List<User> users = userService.selectAllUsersWhoLikedRecordWithLimit(recordService.findOne(Long.parseLong(recordId)),3);
        JSONArray jsonArray = new JSONArray();
        for (User user: users) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("id",user.getId());
            jsonObject.putOnce("newestImageSrc",user.getNewestImageSrc());
            jsonObject.putOnce("firstName",user.getFirstName());
            jsonObject.putOnce("lastName",user.getLastName());
            jsonArray.put(jsonObject);
        }
        return jsonArray.toString();
    }

    @RequestMapping(value = "/changeAutoPlayOfRecord/{recId}",method = RequestMethod.GET,produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    @ResponseBody
    public String changeAutoPlay(@PathVariable("recId") String recordId,@RequestParam String auto){
        System.out.println("Record id: "+recordId);
        Record record = recordService.findOne(Long.parseLong(recordId));
        if (auto.equals("auto_on"))
            record.setAutoplay(true);
        else record.setAutoplay(false);
        recordService.edit(record);
        return "success";
    }

}
