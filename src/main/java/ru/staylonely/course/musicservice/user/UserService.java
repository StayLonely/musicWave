package ru.staylonely.course.musicservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository UserRepository;

    @Autowired
    public UserService(ru.staylonely.course.musicservice.user.UserRepository userRepository) {
        UserRepository = userRepository;
    }

    public void saveUser(User user) throws IllegalAccessException {
        if (UserRepository.findByLogin(user.getLogin()).isPresent()) {
            throw new IllegalAccessException("Login is already exist");
        }

        UserRepository.save(user);
    }

    public List<User> findAll() {
        return UserRepository.findAll();
    }

}
