package ru.staylonely.course.musicservice.user;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Измените имя таблицы (user - ключевое слово в SQL)
public class User {
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
}