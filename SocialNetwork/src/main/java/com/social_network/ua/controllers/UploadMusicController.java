package com.social_network.ua.controllers;

import com.social_network.ua.dao.CommunityMusicDao;
import com.social_network.ua.entity.Community_Music;
import com.social_network.ua.entity.Music;
import com.social_network.ua.services.CommunityMusicService;
import com.social_network.ua.services.MusicService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Rostyslav on 01.12.2016.
 */
@Controller
public class UploadMusicController extends BaseMethods{
    @Autowired
    private MusicService musicService;
    @Autowired
    private CommunityMusicService communityMusicService;

    @RequestMapping(value = "/musicProcess",method = RequestMethod.POST)
    public String saveToTheWall(HttpServletRequest request)
    {
        System.out.println("I am here");
        writeFile(request,"audio");
        return "redirect:/";
    }

    @RequestMapping(value = "/musicToCommunity/{communityId}",method = RequestMethod.POST)
    public String saveToTheCommunity(HttpServletRequest request, @PathVariable("communityId")String communityId)
    {
        System.out.println("I am here");
        Music music = writeFile(request,"communities");
        Community_Music community_music = new Community_Music();
        community_music.setCommunity_id(Long.parseLong(communityId));
        community_music.setMusic_id(music.getId());
        communityMusicService.add(community_music);
        return "redirect:/community/"+communityId;
    }


    public Music writeFile(HttpServletRequest request,String pathAfterResources){
        String path = request.getRealPath("/resources");
        System.out.println("Path"+path);

        Path path1 = Paths.get(path+"\\"+pathAfterResources);
        try {
            Files.createDirectories(path1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiskFileItemFactory d = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(d);
        Music music = null;
        List<FileItem> lst = null;
        try {
            lst = upload.parseRequest(request);
            for (FileItem fileItem: lst){
                if (fileItem.isFormField()==false){
                    System.out.println(fileItem.getName());
                    String file = fileItem.getName().toString();
                    String[] extensions = file.split("\\.");
                    String extension = extensions[extensions.length-1];
                    if (extension.equalsIgnoreCase("mp3") || extension.equalsIgnoreCase("aud") ||
                            extension.equalsIgnoreCase("aif") || extension.equalsIgnoreCase("flac") ||
                            extension.equalsIgnoreCase("iff") || extension.equalsIgnoreCase("m3u") ||
                            extension.equalsIgnoreCase("m4a") || extension.equalsIgnoreCase("m4b") ||
                            extension.equalsIgnoreCase("m4r") || extension.equalsIgnoreCase("mid") ||
                            extension.equalsIgnoreCase("midi") || extension.equalsIgnoreCase("mod") ||
                            extension.equalsIgnoreCase("mpa") || extension.equalsIgnoreCase("ogg") ||
                            extension.equalsIgnoreCase("wav") || extension.equalsIgnoreCase("ra") ||
                            extension.equalsIgnoreCase("ram") || extension.equalsIgnoreCase("sib") ||
                            extension.equalsIgnoreCase("wma")
                            ) {
                        //in this folder, which we created, write our images
                        fileItem.write(new File(path + "/"+pathAfterResources+"/" + fileItem.getName()));
                        music = new Music();
                        music.setNameOfSong(fileItem.getName());
                        music.setUrlOfSong("/resources/"+pathAfterResources+"/" + fileItem.getName());
                        musicService.add(music);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return music;
    }

}

