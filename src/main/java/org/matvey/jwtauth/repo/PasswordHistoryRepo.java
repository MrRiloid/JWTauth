package org.matvey.jwtauth.repo;

import org.matvey.jwtauth.model.PasswordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordHistoryRepo extends JpaRepository<PasswordHistory, Long> {
    List<PasswordHistory> findByUserId(Long userId);
}
