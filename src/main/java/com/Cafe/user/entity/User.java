package com.Cafe.user.entity;

import com.Cafe.config.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@Table(name = "user")
public class User extends BaseEntity {
    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private long phone;

    @Column
    private boolean active;
    @Column
    private boolean admin;
}
