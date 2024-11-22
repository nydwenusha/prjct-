package com.example.Login.Repository.Login;

import com.example.Login.Entity.Login.LoginSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface LoginSessionRepo extends JpaRepository<LoginSession, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM LoginSession s WHERE s.expiresAt < CURRENT_TIMESTAMP")
    void deleteExpiredSessions();

    @Modifying
    @Transactional
    @Query("DELETE FROM LoginSession s WHERE s.sessionId = ?1")
    void deleteSessionRaw(String sessionId);

    Optional<LoginSession> findByUserId(String userId);

    @Modifying
    @Transactional
    @Query("SELECT s FROM LoginSession s WHERE s.userId = ?1")
    List<LoginSession> getSessionsByUserId(String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM LoginSession s WHERE s.sessionId IN ?1")
    void deleteSessionsByIds(List<String> sessionIds);

}
