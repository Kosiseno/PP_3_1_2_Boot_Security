package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController

public class GeneralRestController {

    private UserService userService;

    @Autowired
    public GeneralRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/alll")
    public List<User> getALlUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/user")
    public String getUserProfile(HttpServletRequest httpServletRequest, Model model) {

        model.addAttribute("user", userService.findByUsername(httpServletRequest.getUserPrincipal().getName()));
        return "index";
    }



}
