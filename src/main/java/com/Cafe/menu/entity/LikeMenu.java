package com.Cafe.menu.entity;

import com.Cafe.config.BaseEntity;
import com.Cafe.user.entity.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "like_menu")
public class LikeMenu extends BaseEntity {
    @Column
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
