package org.matvey.jwtauth.service;

import org.matvey.jwtauth.model.PasswordHistory;
import org.matvey.jwtauth.model.User;
import org.matvey.jwtauth.repo.PasswordHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordHistoryService {
    private final PasswordHistoryRepo passwordHistoryRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${service.password.history.count}")
    private int passwordHistoryCount;

    @Autowired
    public PasswordHistoryService(PasswordHistoryRepo passwordHistoryRepo, UserService userService,
                                  PasswordEncoder passwordEncoder) {
        this.passwordHistoryRepo = passwordHistoryRepo;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordHistory changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userService.findByIdOrThrow(userId);

        if (!passwordEncoder.matches(currentPassword, user.getPasswordHash())) {
            throw new RuntimeException("Current password is incorrect");
        }

        checkPasswordHistory(user, newPassword);

        PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setUser(user);
        passwordHistory.setOldPasswordHash(user.getPasswordHash());

        String newPasswordHash = passwordEncoder.encode(newPassword);
        user.setPasswordHash(newPasswordHash);
        userService.saveUser(user);

        passwordHistory.setNewPasswordHash(newPasswordHash);
        return passwordHistoryRepo.save(passwordHistory);
    }

    private PasswordHistory findByIdOrThrow(Long id) {
        return passwordHistoryRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Password history with id " + id + " not found"));
    }

    private void checkPasswordHistory(User user, String newPassword) {
        List<PasswordHistory> passwordHistories =
                passwordHistoryRepo.findByUserId(user.getId());

        if (passwordEncoder.matches(newPassword, user.getPasswordHash())) {
            throw new RuntimeException("New password must be different from current password");
        }

        for (PasswordHistory history : passwordHistories) {
            if (passwordEncoder.matches(newPassword, history.getOldPasswordHash())) {
                throw new RuntimeException(
                        "New password cannot be the same as any of your previous " + passwordHistoryCount + " passwords");
            }
        }
    }
}
