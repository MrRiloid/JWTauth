package org.matvey.jwtauth.service;

import lombok.RequiredArgsConstructor;
import org.matvey.jwtauth.model.PasswordHistory;
import org.matvey.jwtauth.model.User;
import org.matvey.jwtauth.repo.PasswordHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PasswordHistoryService {
    private final PasswordHistoryRepo passwordHistoryRepo;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${service.password.history.count}")
    private int passwordHistoryCount;

    public PasswordHistory changePassword(Long userId, String currentPassword, String newPassword) {
        User user = userService.findByIdOrThrow(userId);

        matchPassword(currentPassword, user.getPassword());

        checkPasswordHistory(user, newPassword);

        PasswordHistory passwordHistory = new PasswordHistory();
        passwordHistory.setUser(user);
        passwordHistory.setOldPasswordHash(user.getPassword());

        String newPasswordHash = passwordEncoder.encode(newPassword);
        user.setPassword(newPasswordHash);
        userService.saveUser(user);

        passwordHistory.setNewPasswordHash(newPasswordHash);
        return passwordHistoryRepo.save(passwordHistory);
    }

    public void matchPassword(String currentPassword, String userPassword) {
        if (!passwordEncoder.matches(currentPassword, userPassword)) {
            throw new RuntimeException("Current password is incorrect");
        }

    }

    private PasswordHistory findByIdOrThrow(Long id) {
        return passwordHistoryRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Password history with id " + id + " not found"));
    }

    private void checkPasswordHistory(User user, String newPassword) {
        List<PasswordHistory> passwordHistories =
                passwordHistoryRepo.findByUserId(user.getId());

        if (passwordEncoder.matches(newPassword, user.getPassword())) {
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
