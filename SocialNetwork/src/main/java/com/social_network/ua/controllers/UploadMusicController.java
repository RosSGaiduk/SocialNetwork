package com.social_network.ua.controllers;

import com.social_network.ua.entity.Music;
import com.social_network.ua.entity.Record;
import com.social_network.ua.entity.User;
import com.social_network.ua.services.ImageService;
import com.social_network.ua.services.MusicService;
import com.social_network.ua.services.RecordService;
import com.social_network.ua.services.UserService;
import com.sun.deploy.net.HttpRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rostyslav on 01.12.2016.
 */
@Controller
public class UploadMusicController extends BaseMethods{
    @Autowired
    private UserService userService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private MusicService musicService;

    @RequestMapping(value = "/musicProcess",method = RequestMethod.POST)
    public String saveToTheWall(HttpServletRequest request)
    {
        System.out.println("I am here");

        String path = request.getRealPath("/resources");
        System.out.println("Path"+path);

        Path path1 = Paths.get(path+"\\"+"audio");
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
            for (FileItem fileItem: lst){
                if (fileItem.isFormField()==false){
                    //in this folder, which we created, write our images
                    fileItem.write(new File(path+"/audio/"+fileItem.getName()));
                    Music music = new Music();
                    music.setNameOfSong(fileItem.getName());
                    music.setUrlOfSong("/resources/audio/"+fileItem.getName());
                    musicService.add(music);
                    System.out.println(fileItem.getName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/";
    }
}

