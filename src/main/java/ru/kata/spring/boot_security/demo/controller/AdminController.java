package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    private UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public String getTest(ModelMap modelMap, Model model) {
        return "all";
    }

    @GetMapping("/")
    public String getSinglePage(HttpServletRequest httpServletRequest, Model model) {
        model.addAttribute("user", userService.findByUsername(httpServletRequest.getUserPrincipal().getName()));
        return "index";
    }
}
