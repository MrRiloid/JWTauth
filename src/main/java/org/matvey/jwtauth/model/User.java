package org.matvey.jwtauth.model;

import jakarta.persistence.*;
import lombok.*;
import org.matvey.jwtauth.enums.Role;

@Entity
@Data
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
}
