package ru.staylonely.course.musicservice.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.staylonely.course.musicservice.track.TrackService;

@Controller
public class HomeController {

    @Autowired
    private TrackService trackService;

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("allTracks", trackService.getAllTracks());
        model.addAttribute("tracks", trackService.getAllTracks());
        return "home";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }
}
