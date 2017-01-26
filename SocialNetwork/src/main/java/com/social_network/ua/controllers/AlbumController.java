package com.social_network.ua.controllers;

import com.social_network.ua.entity.Album;
import com.social_network.ua.entity.User;
import com.social_network.ua.services.AlbumService;
import com.social_network.ua.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Rostyslav on 12.01.2017.
 */
@Controller
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/createAlbumPage", method = RequestMethod.GET)
    public String createAlbumPage(Model model){
        model.addAttribute("album",new Album());
        return "views-album-new";
    }
    @RequestMapping(value = "/createAlbum",method = RequestMethod.POST)
    public String createAlbum(@ModelAttribute Album album){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        album.setUser(user);
        albumService.add(album);
        return "redirect:/photos";
    }
}
