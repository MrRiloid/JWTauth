package org.matvey.jwtauth.controller;

import org.matvey.jwtauth.dto.PasswordChangeRequest;
import org.matvey.jwtauth.dto.PasswordChangeResponse;
import org.matvey.jwtauth.model.PasswordHistory;
import org.matvey.jwtauth.service.PasswordHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/password")
public class PasswordHistoryContoller {
    private final PasswordHistoryService passwordHistoryService;

    @Autowired
    public PasswordHistoryContoller(PasswordHistoryService passwordHistoryService) {
        this.passwordHistoryService = passwordHistoryService;
    }

    @PutMapping("/change/{userId}")
    public ResponseEntity<?> changePassword(@PathVariable Long userId, @RequestBody PasswordChangeRequest passwordChangeRequest) {
        PasswordHistory passwordHistory = passwordHistoryService.changePassword(userId,
                passwordChangeRequest.getCurrentPassword(),
                passwordChangeRequest.getNewPassword());

        return ResponseEntity.ok(new PasswordChangeResponse(
                true,
                "Password changed successfully",
                passwordHistory.getId()
        ));
    }

}
