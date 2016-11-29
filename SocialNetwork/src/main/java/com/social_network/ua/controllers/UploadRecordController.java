package com.social_network.ua.controllers;

import com.social_network.ua.entity.Record;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.services.ImageService;
import com.social_network.ua.services.RecordService;
import com.social_network.ua.services.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
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

/**
 * Created by Rostyslav on 29.11.2016.
 */
@Controller
public class UploadRecordController {
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private RecordService recordService;
    @RequestMapping(value = "/process1",method = RequestMethod.POST)
    public String saveToTheWall(HttpServletRequest request)
    {
        System.out.println("I am here");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //getting path to folder which we want
        String path = request.getRealPath("/resources");

        List<Record> records = recordService.findAll();
        Record record = records.get(records.size()-1);

        System.out.println("Id: "+record.getUser().getId());

        //creating a folder inside of this folder
        Path path1 = Paths.get(path+"\\"+record.getUser().getId()+"\\wall");
        try {
            Files.createDirectories(path1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiskFileItemFactory d = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(d);

        List<FileItem> lst = null;
        try {
            lst = upload.parseRequest(request);
            User user = userService.findOne(Long.parseLong(authentication.getName()));
            for (FileItem fileItem: lst){
                if (fileItem.isFormField()==false){
                    //in this folder, which we created, write our images
                    fileItem.write(new File(path+"/"+record.getUser().getId()+"/wall/"+fileItem.getName()));
                    record.setUrlImage("/resources/"+record.getUser().getId()+"/wall/"+fileItem.getName());
                    recordService.edit(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("size: "+lst.size());

        //return "redirect:/";
        return "redirect:/user/"+record.getUser().getId();
    }
}
