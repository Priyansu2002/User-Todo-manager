package com.codejava.login.controller;

import com.codejava.login.entity.User;
import com.codejava.login.repository.UserRepository;
import com.codejava.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    public UserRepository repo;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String viewHomePage(){
        return "index";
    }
    @GetMapping("/register")
    public String showSignUpForm(Model model){
        model.addAttribute("user",new User());

        return "signup_form";

    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model) {
        List<User> listUsers = repo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";  // Return the view name for the user list page
    }

    @GetMapping("/search")
    public String searchUsers(@RequestParam("query") String query, Model model) {
        List<User> searchResults = userService.searchUsers(query);
        model.addAttribute("searchResults", searchResults);
        return "users";
    }
    // Handle user update (GET method to show the form)
    @GetMapping("users/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update_user"; // Create this template
    }

    // Handle user update (POST method to save the changes)
    @PostMapping("users/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.updateUser(id, user);
        return "redirect:/users";
    }

    // Handle user delete
    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user){

        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String encodedPassword=encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        repo.save(user);

        return "register_success";
    }



}
