package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        model.addAttribute("user", currentUser);
        return "user/show";
    }

    @GetMapping("/changePassword")
    public String changePassword(@RequestParam Long id, Model model) {
        User user = userService.get(id);
        model.addAttribute("user", user);
        return "user/changePassword";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(@RequestParam Long id, @RequestParam String newPassword) {
        userService.updatePassword(id, newPassword);
        return "redirect:/user";
    }
}