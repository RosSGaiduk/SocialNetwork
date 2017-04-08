package com.social_network.ua.controllers;

import com.social_network.ua.session_objects.MySessionObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by Rostyslav on 05.04.2017.
 */
@Controller
public class SessionController {

    @RequestMapping(value = "/sessionPage",method = RequestMethod.GET)
    public String getSessionPage(HttpSession session, Model model){
        /*System.out.println("Equals to null "+(session.getAttribute("mySession")==null));
        if (session.getAttribute("mySession")==null) session.setAttribute("mySession","Hello world");*/
        MySessionObject sessionObject = (MySessionObject) session.getAttribute("mySession");
        if (sessionObject==null)
            sessionObject = new MySessionObject();
        session.setAttribute("mySession",sessionObject);
        model.addAttribute("mySessionModel",sessionObject);
        return "views-training-sessions";
    }

    @RequestMapping(value = "/changeSessionValue",method = RequestMethod.POST)
    public String changeMethodValue(@ModelAttribute("mySessionModel")MySessionObject sessionObject, HttpSession session){
        //String sessionTitle = (String)session.getAttribute("mySession");
        //if (session.getAttribute("mySession")==null)
            //session.setAttribute("mySession",newTitle);
        //MySessionObject sessionObject = (MySessionObject) session.getAttribute("mySession");
        //if (sessionObject==null)
            //sessionObject = new MySessionObject();
        //MySessionObject sessionObject = (MySessionObject) session.getAttribute("mySession");
        sessionObject.setCountChanged(sessionObject.getCountChanged()+1);
        //sessionObject.setTitle(title);
        session.setAttribute("mySession",sessionObject);
        return "redirect:/sessionPage";
    }
}
