package com.social_network.ua.controllers;

import com.social_network.ua.entity.Album;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.enums.AlbumName;
import com.social_network.ua.services.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by Rostyslav on 26.03.2017.
 */
@Controller
public class UploadImageController {
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private AlbumService albumService;
    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/loadImageToAlbum/{id}",method = RequestMethod.POST)
    public String loadImageToAlbum(HttpServletRequest request, @PathVariable("id")String id){
        Album album = albumService.findOne(Long.parseLong(id));
        System.out.println("Album: "+album.getName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String path = request.getRealPath("/resources");
        Path path1 = Paths.get(path+"\\users");
        try {
            Files.createDirectories(path1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DiskFileItemFactory d = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(d);

        try {
            //getting a list of items, from which, we will get our image
            List<FileItem> lst = upload.parseRequest(request);
            User user = userService.findOne(Long.parseLong(authentication.getName()));
            for (FileItem fileItem : lst) {
                if (fileItem.isFormField() == false) {
                    String file = fileItem.getName().toString();
                    String[] extensions = file.split("\\.");
                    String extension = extensions[extensions.length - 1];
                    if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") ||
                            extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("gif")
                            ||  extension.equalsIgnoreCase("jpeg")
                            ) {
                        System.out.println("Extension: "+extension);
                        User_Images image = new User_Images();
                        image.setAlbum(album);
                        image.setCountLikes(0);
                        image.setUser(user);
                        image.setDateOfImage(new Date(System.currentTimeMillis()));
                        String nameOfImage = "imageOf_"+authentication.getName()+"_";
                        boolean exists = true;
                        while (exists){
                            Random random = new Random();
                            int num = random.nextInt(Integer.MAX_VALUE-1);
                            nameOfImage+=num+"."+extension;
                            User_Images user_images = imageService.findOneByUserIdAndName(user,"/resources/users/"+nameOfImage);
                            if (user_images==null)
                                exists = false;
                            else nameOfImage = "imageOf_"+authentication.getName()+"_";
                        }
                        image.setUrlOfImage("/resources/users/"+nameOfImage);
                        imageService.add(image);
                        if (album.getName().equals(AlbumName.MY_PAGE_PHOTOS.toString())){
                            user.setNewestImageSrc(image.getUrlOfImage());
                            user.setNewestImageId(image.getId());
                            userService.edit(user);
                            messageService.updateMessagesImageOfUser(user,user.getNewestImageSrc());
                            recordService.updateUserImageSrcOfRecords(user);
                            commentService.updateCommentsNewestImageSrcOfUser(user);
                        }
                        albumService.edit(album);
                        System.out.println("Url if image "+image.getUrlOfImage());
                        fileItem.write(new File(path+"/users/"+nameOfImage));
                    }
                }
            }
        } catch (Exception ex){

        }
        return "redirect:/photosOf/"+authentication.getName()+"/"+album.getName();
    }
}
