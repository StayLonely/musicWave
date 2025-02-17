package ru.staylonely.course.musicservice.track;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.staylonely.course.musicservice.user.User;
import ru.staylonely.course.musicservice.user.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class TrackController {

    @Autowired
    private TrackService trackService;

    @Autowired
    private UserService userService;

    @GetMapping("/collection")
    public String userCollection(Model model) {
        User user = userService.getCurrentUser();
        model.addAttribute("currentUser", user);
        model.addAttribute("tracks", user.getFavoriteTracks());
        return "collection";
    }

    @GetMapping("/upload")
    public String uploadForm(Model model) {
        model.addAttribute("track", new Track());
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadTrack(
            @ModelAttribute Track track,
            @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes
    ) {
        try {
            trackService.uploadTrack(file, track.getTitle(), track.getArtist());
            redirectAttributes.addFlashAttribute("success", "Трек успешно загружен");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка при загрузке файла");
        } catch (MaxUploadSizeExceededException e) {
            redirectAttributes.addFlashAttribute("error", "Файл слишком большой! Максимальный размер: 50MB");
        }
        return "redirect:/collection";
    }

    @PostMapping("/toggle-favorite/{trackId}")
    public String toggleFavorite(@PathVariable Long trackId) {
        User user = userService.getCurrentUser();
        Track track = trackService.getTrackById(trackId);

        if(user.getFavoriteTracks().contains(track)) {
            user.getFavoriteTracks().remove(track);
        } else {
            user.getFavoriteTracks().add(track);
        }

        userService.registerUser(user); // Используем правильный метод
        return "redirect:/collection";
    }




}
