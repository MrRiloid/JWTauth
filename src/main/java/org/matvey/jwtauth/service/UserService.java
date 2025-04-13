package org.matvey.jwtauth.service;

import org.matvey.jwtauth.model.User;
import org.matvey.jwtauth.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return findByIdOrThrow(id);
    }

    public User createUser(User user) {
        System.out.println(user.getPasswordHash());
        checkByUsernameOrThrow(user.getUsername());
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepo.save(user);
    }

    public User updateUser(Long id, User user) {

        checkByUsernameOrThrow(user.getUsername());
        return userRepo.save(user);
    }

    public void deleteUser(Long id) {
        findByIdOrThrow(id);
        userRepo.deleteById(id);
    }

    private User findByIdOrThrow(Long id) {
        return userRepo.findById(id).orElseThrow(() -> new RuntimeException("User with id " + id + " not found"));
    }

    private void checkByUsernameOrThrow(String username) {
        if (userRepo.existsByUsername(username)) {
            throw new RuntimeException("User with name " + username + " already exists");
        }
    }

    private void checkIdFromUser(Long id, User user) {
        if (!user.getId().equals(id)) {
            throw new RuntimeException("User with id " + user.getId() + " does not match id " + id);
        }
    }
}
