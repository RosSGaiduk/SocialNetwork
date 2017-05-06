package com.social_network.ua.controllers;

import com.social_network.ua.entity.Message;
import com.social_network.ua.entity.MessagesUpdator;
import com.social_network.ua.entity.User;
import com.social_network.ua.enums.RecordType;
import com.social_network.ua.services.MessageService;
import com.social_network.ua.services.MessagesUpdatorService;
import com.social_network.ua.services.RecordService;
import com.social_network.ua.services.UserService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Rostyslav on 23.04.2017.
 */
@Controller
public class UploadItemsToMessage {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessagesUpdatorService messagesUpdatorService;

    @RequestMapping(value = "/loadItemToMessages/{id}", method = RequestMethod.POST, produces = {"text/html; charset/UTF-8; charset=windows-1251"})
    public String loadItemToMessages(@PathVariable("id")String userId,HttpServletRequest request) throws Exception {
        System.out.println("I am here "+userId);
        String path = request.getRealPath("/resources");
        Path path1 = Paths.get(path + "\\messagesItems");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long authId = Long.parseLong(authentication.getName());
        User authUser = userService.findOne(authId);
        User userTo = userService.findOne(Long.parseLong(userId));
        Message message = new Message();
        message.setUserFrom(authUser);
        message.setUserTo(userTo);
        Files.createDirectories(path1);
        DiskFileItemFactory d = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(d);
        Enumeration<String> headers = request.getParameterNames();
        while (headers.hasMoreElements()){
            System.out.println("Next element "+headers.nextElement());
        }
        List<FileItem> lst = null;
        lst = upload.parseRequest(request);

        for (FileItem fileItem : lst) {
            if (fileItem.isFormField() == false) {
                String file = fileItem.getName().toString();
                String[] extensions = file.split("\\.");
                String extension = extensions[extensions.length - 1];
                if (extension.equalsIgnoreCase("png") || extension.equalsIgnoreCase("jpg") ||
                        extension.equalsIgnoreCase("bmp") || extension.equalsIgnoreCase("gif")
                        || extension.equalsIgnoreCase("jpeg")
                        ) {
                    fileItem.write(new File(path + "/messagesItems/" + fileItem.getName()));
                    message.setType(RecordType.IMAGE.toString());
                    message.setUrlOfItem("/resources/messagesItems/" + fileItem.getName());
                    message.setDateOfMessage(new Date(System.currentTimeMillis()));
                    messageService.add(message);
                    MessagesUpdator messagesUpdator = messagesUpdatorService.findOneBy2Ids(authId,Long.parseLong(userId));
                    if (messagesUpdator == null) {
                        messagesUpdatorService.add(new MessagesUpdator(authId, Long.parseLong(userId)));
                    }
                    else {
                        messagesUpdator.setCountMessages(messagesUpdator.getCountMessages()+1);
                        messagesUpdatorService.edit(messagesUpdator);
                    }
                }
            }
        }
        return "redirect:/messagesWithUser/"+userId;
    }
}
