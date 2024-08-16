package com.codejava.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMessage", "Invalid login credentials. Please check your username and password.");
        }

        model.addAttribute("username", username != null ? username : "");
        model.addAttribute("password", password != null ? password : "");

        return "login";  // Return the view name for the login page
    }
}
