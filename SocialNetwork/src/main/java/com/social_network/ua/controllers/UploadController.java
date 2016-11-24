package com.social_network.ua.controllers;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.file.*;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rostyslav on 24.11.2016.
 */
@Controller
@RequestMapping(value = "upload",method = RequestMethod.GET)
public class UploadController {

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

            for (FileItem fileItem: lst){
                if (fileItem.isFormField()==false) {
                    //in this folder, which we created, write our images
                    fileItem.write(new File(path+"/"+authentication.getName()+"/"+fileItem.getName()));
                }
            }

        } catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "views-base-friends";
    }
}
