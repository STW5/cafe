package com.Cafe.user.common;

import com.Cafe.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UserJoinForm {
    private String username;
    private String password;
    private String name;

    public User toEntity(){
        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .active(true)
                .admin(false)
                .build();
    }
}
