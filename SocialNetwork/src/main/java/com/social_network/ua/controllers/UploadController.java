package com.social_network.ua.controllers;

import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.services.ImageService;
import com.social_network.ua.services.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.file.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 24.11.2016.
 */
@Controller
@RequestMapping(value = "upload",method = RequestMethod.GET)
public class UploadController {

    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "/process",method = RequestMethod.POST)
    public String save(HttpServletRequest request)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //getting path to folder which we want
        String path = request.getRealPath("/resources");

        //creating a folder inside of this folder
        Path path1 = Paths.get(path+"\\"+authentication.getName());
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
            for (FileItem fileItem: lst){
                if (fileItem.isFormField()==false){
                    String file = fileItem.getName().toString();
                    String[] extensions = file.split("\\.");
                    String extension = extensions[extensions.length-1];
                    if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") ||
                            extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("gif")
                            ) {
                        //in this folder, which we created, write our images
                        fileItem.write(new File(path + "/" + authentication.getName() + "/" + fileItem.getName()));
                        User_Images user_images = new User_Images();
                        //System.out.println("File item: "+fileItem.getName());
                        //String pathInDB = "/resources/"+authentication.getName()+"/"+fileItem.getName();
                        user_images.setUrlOfImage("/resources/" + authentication.getName() + "/" + fileItem.getName());
                        user_images.setDateOfImage(new Date(System.currentTimeMillis()));
                        user_images.setUser(user);
                        user.setNewestImageSrc("/resources/" + authentication.getName() + "/" + fileItem.getName());
                        userService.edit(user);
                        imageService.add(user_images);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "redirect:/";
    }
}
