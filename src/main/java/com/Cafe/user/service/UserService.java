package com.Cafe.user.service;

import com.Cafe.user.common.UserJoinForm;
import com.Cafe.user.entity.User;
import com.Cafe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User join(UserJoinForm userJoinForm) {
        if(userRepository.existsByUsername(userJoinForm.getUsername()))
            return null;
        return userRepository.save(userJoinForm.toEntity());
    }


}
