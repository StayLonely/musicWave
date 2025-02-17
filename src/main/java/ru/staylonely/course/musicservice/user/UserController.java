package ru.staylonely.course.musicservice.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/profile")
    public String my(Model model) {
        model.addAttribute("profile", userService.getCurrentUser());
        return "profile";
    }



}
