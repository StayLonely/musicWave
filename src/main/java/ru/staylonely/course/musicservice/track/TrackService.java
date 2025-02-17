package ru.staylonely.course.musicservice.track;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import ru.staylonely.course.musicservice.user.User;
import ru.staylonely.course.musicservice.user.UserRepository;
import ru.staylonely.course.musicservice.user.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserService userService;

    @Value("${upload.directory}")
    private String uploadDir;

    @Autowired
    public TrackService(
            TrackRepository trackRepository,
            @Value("${upload.directory}") String uploadDir
    ) {
        this.trackRepository = trackRepository;
        this.uploadDir = uploadDir;

        // Создаем директорию, если ее нет
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Cannot create upload directory", e);
        }
    }

    public Track uploadTrack(MultipartFile file, String title, String artist) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Track track = new Track();
        track.setTitle(title);
        track.setArtist(artist);
        track.setFilePath(fileName);
        track.setUploadDate(LocalDateTime.now());

        Track savedTrack = trackRepository.save(track);

        User currentUser = userService.getCurrentUser();
        currentUser.getFavoriteTracks().add(savedTrack);
        userService.updateUser(currentUser);

        return savedTrack;
    }

    public void deleteTrack(Long trackId) {
        trackRepository.deleteById(trackId);
    }

    public Track getTrackById(Long trackId) {
        return trackRepository.findById(trackId).orElse(null);
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }
}
