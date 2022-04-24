package com.freshvotes.controller;

import com.freshvotes.domain.User;
import com.freshvotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(ModelMap modelMap) {
        modelMap.put("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String createAccount(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/login";
    }
}
