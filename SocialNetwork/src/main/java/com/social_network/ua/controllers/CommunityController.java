package com.social_network.ua.controllers;

import com.social_network.ua.entity.Community;
import com.social_network.ua.entity.Record;
import com.social_network.ua.entity.User;
import com.social_network.ua.services.CommunityService;
import com.social_network.ua.services.CommunitySubscriberService;
import com.social_network.ua.services.RecordService;
import com.social_network.ua.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.List;

/**
 * Created by Rostyslav on 06.03.2017.
 */
@Controller
public class CommunityController {

    @Autowired
    private CommunityService communityService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecordService recordService;
    @Autowired
    private CommunitySubscriberService communitySubscriberService;

    @RequestMapping(value = "/communities",method = RequestMethod.GET)
    public String communitiesPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        List<Community> communities = communityService.findAll();
        model.addAttribute("communities",communities);
        return "views-communities-all";
    }

    @RequestMapping(value = "/newCommunity",method = RequestMethod.GET)
    public String newCommunity(Model model){
        model.addAttribute("community",new Community());
        return "views-communities-new";
    }


    @RequestMapping(value = "/createCommunity",method = RequestMethod.POST)
    public String createCommunity(@ModelAttribute Community community){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findOne(Long.parseLong(authentication.getName()));
        community.setUser(user);
        Date date = new Date(System.currentTimeMillis());
        community.setDate(date);
        communityService.add(community);
        return "redirect:/";
    }

    @RequestMapping(value = "/community/{id}",method = RequestMethod.GET)
    public String communitySelected(Model model, @PathVariable("id")String id){
        Community community = communityService.findOne(Long.parseLong(id));
        model.addAttribute("community",community);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (communityService.findUserOfCommunity(Long.parseLong(id)).getId()==Long.parseLong(authentication.getName()))
            model.addAttribute("belong",true);
        else model.addAttribute("belong",false);
        List<Record> records = recordService.findAllByCommunity(community);
        System.out.println("Size records: "+records.size());
        model.addAttribute("records",records);
        model.addAttribute("userAuth",userService.findOne(Long.parseLong(authentication.getName())));
        model.addAttribute("subscribed",communitySubscriberService.checkIfUserSubscribed(Long.parseLong(authentication.getName()),Long.parseLong(id)));
        model.addAttribute("subscribers",userService.findAllUsersOfCommunity(community,6));
        return "views-communities-selected";
    }
}
