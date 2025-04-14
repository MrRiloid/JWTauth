package org.matvey.jwtauth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeResponse {
    private boolean success;
    private String message;
    private Long passwordHistoryId;
}