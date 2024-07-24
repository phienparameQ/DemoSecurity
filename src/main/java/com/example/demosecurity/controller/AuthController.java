package com.example.demosecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    @RequestMapping("/auth/login/form")
    public String login(){
        return "login/index";
    }

    @RequestMapping("/auth/login/success")
    public String success(Model model){
        model.addAttribute("message","Dang nhap thanh cong");
        return "forward:/auth/login/form";
    }

    @RequestMapping("/auth/login/error")
    public String error(Model model){
        model.addAttribute("message","Sai thông tin đăng nhập");
        return "forward:/auth/login/form";
    }

    @RequestMapping("/auth/logout/success")
    public String logout(Model model){
        model.addAttribute("message","Dang xuat thanh cong");
        return "forward:/auth/login/form";
    }

    @RequestMapping("/auth/access/denied")
    public String admins(Model model){
        model.addAttribute("message","hello ban khong có quyền truy xuất");
        return "login/index";
    }
}
