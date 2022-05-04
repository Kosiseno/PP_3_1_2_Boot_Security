package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
public class AdminController {

    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getAdminPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.findAllUsers());
        return "admin";
    }


    @GetMapping("/admin/users/{id}")
    public String getUserById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "editUser";
    }

    @GetMapping("/admin/users/addUser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/admin/users/addUser")
    public String addUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/addUser";
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/admin/users/edit")
    public String editUser(@ModelAttribute("user") @Valid User user,
                           BindingResult bindingResult) {

        User injectUser = userService.findUserById(user.getId()); //вероятнее всего так делать не правильно, но пока не пойму как исправить
        user.setPassword(injectUser.getPassword());
        user.setRoles(injectUser.getRoles());
//        if (bindingResult.hasErrors())
//            return "editUser";
        userService.editUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/users/delete")
    public String deleteUserById(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/delete")
    public String deleteUserInProfile(@RequestParam(name = "id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUserProfile(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "editUser";

    }

}
