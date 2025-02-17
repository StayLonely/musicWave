package ru.staylonely.course.musicservice.track;

import jakarta.persistence.*;
import ru.staylonely.course.musicservice.user.User;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Track {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;
    private String filePath;
    private LocalDateTime uploadDate;
    private int duration; // в секундах

    @ManyToMany(mappedBy = "favoriteTracks")
    private Set<User> likedByUsers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Set<User> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<User> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    // Конструкторы, геттеры, сеттеры
}
