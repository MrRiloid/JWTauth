package org.matvey.jwtauth.controller;

import jakarta.validation.Valid;
import org.matvey.jwtauth.dto.UserDto;
import org.matvey.jwtauth.model.User;
import org.matvey.jwtauth.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        List<User> users = userService.getUsers();
        List<UserDto> userDtos = users
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();

        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userService.createUser(user);
        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User updatedUser = userService.updateUser(id, user);
        UserDto updatedUserDto = modelMapper.map(updatedUser, UserDto.class);
        return ResponseEntity.ok(updatedUserDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}