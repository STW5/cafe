package com.Cafe.menu.repository;

import com.Cafe.menu.entity.LikeMenu;
import com.Cafe.menu.entity.Menu;
import com.Cafe.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeMenuRepository extends JpaRepository<LikeMenu, Long> {
    LikeMenu findByUserAndMenu(User user, Menu menu);
}
