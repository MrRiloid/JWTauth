package org.matvey.jwtauth.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "password_history")
public class PasswordHistory {
    @Id
    private Long id;
    private String oldPasswordHash;
    private String newPasswordHash;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
