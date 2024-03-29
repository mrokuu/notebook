package com.example.nootebook.repository;

import com.example.nootebook.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    @Transactional
    @Modifying
    @Query("delete from User u where u.id = :id")
    void deleteById(Long id);

    User findByEmail(String email);

    User findByResetPasswordToken(String token);

    User findByActivationToken(String token);
}
