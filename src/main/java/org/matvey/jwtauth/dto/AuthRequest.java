package org.matvey.jwtauth.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    private String username;
    private String password;
}
