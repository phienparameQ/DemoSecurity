package com.example.demosecurity.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @Autowired
    HttpServletRequest request;

    @GetMapping("/home/index")
    public String home(Model model){
        model.addAttribute("message","This is index");
        return "home/index";
    }

    @GetMapping("/home/about")
    public String about(Model model){
        model.addAttribute("message","This is about page");
        return "home/index";
    }


    @RequestMapping("/home/admins")
    public String admins(Model model){
        if (!request.isUserInRole("ADMIN")){
            return "redirect:/auth/access/denied";
        }
        model.addAttribute("message","hello administrator");
        return "home/index";
    }

    @RequestMapping("/home/user")
    public String user(Model model){
        if (!request.isUserInRole("ADMIN") || request.isUserInRole("USER")){
            return "redirect:/auth/access/denied";
        }
        model.addAttribute("message","hello staff");
        return "home/index";
    }

    @RequestMapping("/home/authenticated")
    public String authenticated(Model model){
        if (request.getRemoteUser() == null){
            return "redirect:/auth/access/denied";
        }
        model.addAttribute("message","hello authenticated user");
        return "home/index";
    }
}
