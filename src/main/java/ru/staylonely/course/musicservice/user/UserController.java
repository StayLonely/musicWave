package ru.staylonely.course.musicservice.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/create_user")
    public String createUser(@ModelAttribute User user) throws IllegalAccessException {
        userService.saveUser(user);
        return "redirect:/register";
    }

}
