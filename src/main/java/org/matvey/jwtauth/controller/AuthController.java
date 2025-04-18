package org.matvey.jwtauth.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.matvey.jwtauth.dto.AuthRequest;
import org.matvey.jwtauth.dto.AuthResponse;
import org.matvey.jwtauth.enums.Role;
import org.matvey.jwtauth.model.User;
import org.matvey.jwtauth.service.UserService;
import org.matvey.jwtauth.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final JwtUtil jwtUtil;

    @PostMapping("/reg")
    public ResponseEntity<?> registration(@Valid @RequestBody AuthRequest authRequest) {

        User user = User.builder().
                username(authRequest.getUsername()).
                password(authRequest.getPassword()).
                role(Role.ROLE_USER).
                build();

        String token = jwtUtil.createToken(user);

        return ResponseEntity.ok(new AuthResponse(token));
    }

}
