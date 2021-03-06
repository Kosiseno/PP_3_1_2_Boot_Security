package ru.kata.spring.boot_security.demo.controller;

import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String startPage() {
        return "index";
    }



    @GetMapping("/user")
    public String getUserProfile(HttpServletRequest httpServletRequest, Model model) {

        model.addAttribute("user", userService.findByUsername(httpServletRequest.getUserPrincipal().getName()));
        return "user";
    }

    @PostMapping("user/edit")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {
//        if (bindingResult.hasErrors())
//            return "user";
        User injectUser = userService.findUserById(user.getId());
        user.setUsername(injectUser.getUsername());
        user.setPassword(injectUser.getPassword());
        user.setRoles(injectUser.getRoles());
        userService.editUser(user);
        return "redirect:/user";
    }
}
