package org.matvey.jwtauth.model;

import jakarta.persistence.*;
import lombok.Data;
import org.matvey.jwtauth.enums.Role;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String passwordHash;
    @Enumerated(EnumType.STRING)
    private Role role;
}
