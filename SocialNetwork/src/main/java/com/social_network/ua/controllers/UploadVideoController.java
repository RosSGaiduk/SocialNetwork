package com.social_network.ua.controllers;

import com.social_network.ua.entity.Music;
import com.social_network.ua.entity.Video;
import com.social_network.ua.services.CommentService;
import com.social_network.ua.services.UserService;
import com.social_network.ua.services.VideoService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

/**
 * Created by Rostyslav on 28.03.2017.
 */
@Controller
public class UploadVideoController {
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private VideoService videoService;

    @RequestMapping(value = "/videoProcess",method = RequestMethod.POST)
    public String loadVideo(HttpServletRequest request){
        System.out.println("VideoProcess!!!");
        String path = request.getRealPath("/resources");
        Path path1 = Paths.get(path+"\\videos");

        try {
            Files.createDirectories(path1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiskFileItemFactory d = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(d);
        Video video = null;
        List<FileItem> lst = null;
        try {
            lst = upload.parseRequest(request);
            for (FileItem fileItem : lst) {
                if (fileItem.isFormField() == false) {
                    String file = fileItem.getName().toString();
                    String[] extensions = file.split("\\.");
                    String extension = extensions[extensions.length - 1];
                    if (extension.equalsIgnoreCase("mp4")){
                        fileItem.write(new File(path + "/videos/" + fileItem.getName()));
                        video = new Video();
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        video.setUser(userService.findOne(Long.parseLong(authentication.getName())));
                        video.setName(fileItem.getName());
                        video.setDate(new Date(System.currentTimeMillis()));
                        video.setUrl("/resources/videos/"+fileItem.getName());
                        videoService.add(video);
                    }
                }
            }
        } catch (Exception e) {
        }
        return "redirect:/videoProcessLoadingBannerPage/"+video.getId();
    }
}
