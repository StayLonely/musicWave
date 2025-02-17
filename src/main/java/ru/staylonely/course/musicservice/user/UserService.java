package ru.staylonely.course.musicservice.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository UserRepository;

    @Autowired
    public UserService(ru.staylonely.course.musicservice.user.UserRepository userRepository) {
        UserRepository = userRepository;
    }
    // Для регистрации нового пользователя
    @Transactional
    public void registerUser(User user) {
        if (UserRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new RuntimeException("Логин уже занят");
        }
        UserRepository.save(user);
    }

    // Для обновления существующего пользователя
    @Transactional
    public void updateUser(User user) {
        UserRepository.save(user);
    }

    public List<User> findAll() {
        return UserRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return UserRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Пользователь не аутентифицирован");
        }

        String login = authentication.getName();

        return UserRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Пользователь с логином '" + login + "' не найден"
                ));
    }

}
