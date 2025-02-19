package ru.staylonely.course.musicservice.user;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.staylonely.course.musicservice.track.Track;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users") // Измените имя таблицы (user - ключевое слово в SQL)
public class User implements UserDetails {
    @Id
    @SequenceGenerator(
            name = "user_sequence", // Исправьте опечатку: seqeunce -> sequence
            sequenceName = "user_sequence", // И здесь
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence" // И здесь
    )
    private Long id;

    private String nickname;
    private String login;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_favorite_tracks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "track_id")
    )
    private Set<Track> favoriteTracks = new HashSet<>();

    // Конструктор без id (для JPA и создания новых объектов)
    public User(String nickname, String login, String password) {
        this.nickname = nickname;
        this.login = login;
        this.password = password;
    }

    public User() {
        // Обязательный пустой конструктор
    }

    // Добавьте сеттеры для всех полей
    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Геттеры
    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login; // Используем login как username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<Track> getFavoriteTracks() {
        return favoriteTracks;
    }

    public void setFavoriteTracks(Set<Track> favoriteTracks) {
        this.favoriteTracks = favoriteTracks;
    }
}