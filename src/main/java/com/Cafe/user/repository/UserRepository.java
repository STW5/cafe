package com.Cafe.user.repository;

import com.Cafe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public boolean existsByUsername(String username);
}
