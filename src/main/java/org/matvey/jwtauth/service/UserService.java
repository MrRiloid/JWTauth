package org.matvey.jwtauth.service;

import lombok.RequiredArgsConstructor;
import org.matvey.jwtauth.enums.Role;
import org.matvey.jwtauth.model.User;
import org.matvey.jwtauth.repo.UserRepo;
import org.matvey.jwtauth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User getUserById(Long id) {
        return findByIdOrThrow(id);
    }

    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public User createUser(User user) {
        checkByUsernameOrThrow(user.getUsername());
        if (user.getRole() == null) {
            user.setRole(Role.ROLE_USER);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return saveUser(user);
    }

    public User updateUser(Long id, User user) {

        checkByUsernameOrThrow(user.getUsername());
        return saveUser(user);
    }

    public void deleteUser(Long id) {
        findByIdOrThrow(id);
        userRepo.deleteById(id);
    }

    protected User saveUser(User user) {
        return userRepo.save(user);
    }

    protected User findByIdOrThrow(Long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new RuntimeException("User with id " + id + " not found"));
    }

    protected void checkByUsernameOrThrow(String username) {
        if (userRepo.existsByUsername(username)) {
            throw new RuntimeException("User with name " + username + " already exists");
        }
    }

    protected void checkIdFromUser(Long id, User user) {
        findByIdOrThrow(user.getId());
        if (!user.getId().equals(id)) {
            throw new RuntimeException("User with id " + user.getId() + " does not match id " + id);
        }
    }
}
