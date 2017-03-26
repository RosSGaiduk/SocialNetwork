package com.social_network.ua.controllers;

import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.Record;
import com.social_network.ua.entity.User;
import com.social_network.ua.entity.User_Images;
import com.social_network.ua.enums.RecordType;
import com.social_network.ua.services.CommunityService;
import com.social_network.ua.services.RecordService;
import com.social_network.ua.services.UserService;
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

/**
 * Created by Rostyslav on 06.03.2017.
 */
@Controller
public class UploadCommunityController {
    @Autowired
    private CommunityService communityService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;


    @RequestMapping(value = "/uploadCommunityLogo/{id}",method = RequestMethod.POST)
    public String uploadCommunityLogo(HttpServletRequest request, @PathVariable("id") String id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Community community = communityService.findOne(Long.parseLong(id));
        //getting path to folder which we want
        String path = request.getRealPath("/resources");

        //creating a folder inside of this folder
        Path path1 = Paths.get(path+"\\communities");
        try {
            Files.createDirectories(path1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiskFileItemFactory d = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(d);

        try {
            List<FileItem> lst = upload.parseRequest(request);
            for (FileItem fileItem: lst){
                if (fileItem.isFormField()==false){
                    String file = fileItem.getName().toString();
                    String[] extensions = file.split("\\.");
                    String extension = extensions[extensions.length-1];
                    if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") ||
                            extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("gif")
                            ) {
                        fileItem.write(new File(path + "/communities/" + fileItem.getName()));
                        community.setUrlImage("/resources/communities/" + fileItem.getName());
                        communityService.edit(community);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return "fail";
        }

        return "redirect:/community/"+id;
    }


    @RequestMapping(value = "/uploadRecordToCommunity/{idCommunity}/{text}",method = RequestMethod.POST)
    public String uploadRecordToCommunity(HttpServletRequest request, @PathVariable("idCommunity") String id,
                                          @PathVariable("text")String text){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Community community = communityService.findOne(Long.parseLong(id));
        User user = userService.findOne(Long.parseLong(authentication.getName()));

        Record record = new Record();
        record.setUserFrom(user);
        record.setDateOfRecord(new Date(System.currentTimeMillis()));
        record.setUrlUserImagePattern(user.getNewestImageSrc());
        record.setCommunity(community);

        if (text.equals("there is no text here just sent to avoid mistake")) record.setText("");
        else record.setText(text);

            record.setType("");
            recordService.add(record);
            communityService.edit(community);
            //return "redirect:/community/"+id;
        //}

        //getting path to folder which we want
        String path = request.getRealPath("/resources");

        //creating a folder inside of this folder
        Path path1 = Paths.get(path+"\\communities");
        try {
            Files.createDirectories(path1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DiskFileItemFactory d = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(d);

        try {
            List<FileItem> lst = upload.parseRequest(request);
            for (FileItem fileItem: lst){
                if (fileItem.isFormField()==false){
                    String file = fileItem.getName().toString();
                    String[] extensions = file.split("\\.");
                    String extension = extensions[extensions.length-1];
                    //якщо файлу немає тому, що якщо файл є, то мінімум має бути length == 2
                    if (extensions.length==1){
                        record.setType(RecordType.TEXT.toString());
                        recordService.edit(record);
                        break;
                    }
                    if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") ||
                            extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("gif")
                            ) {
                        fileItem.write(new File(path + "/communities/" + fileItem.getName()));
                        record.setHasImage(true);
                        record.setType(RecordType.IMAGE.toString());
                        record.setUrl("/resources/communities/" + fileItem.getName());
                        recordService.edit(record);
                        communityService.edit(community);
                    } else if (extension.equalsIgnoreCase("mp3") || extension.equalsIgnoreCase("aud") ||
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
                        fileItem.write(new File(path + "/communities/" + fileItem.getName()));
                        record.setType(RecordType.AUDIO.toString());
                        record.setUrl("/resources/communities/" + fileItem.getName());
                        record.setNameRecord(fileItem.getName());
                        recordService.edit(record);
                        communityService.edit(community);
                    } else if (extension.equalsIgnoreCase("mp4")){
                        fileItem.write(new File(path + "/communities/" + fileItem.getName()));
                        record.setType(RecordType.VIDEO.toString());
                        record.setUrl("/resources/communities/" + fileItem.getName());
                        recordService.edit(record);
                        communityService.edit(community);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return "fail";
        }
        return "redirect:/community/"+id;
    }
}
