package com.Cafe.user.service;

import com.Cafe.user.common.UserJoinForm;
import com.Cafe.user.common.UserLoginForm;
import com.Cafe.user.entity.User;
import com.Cafe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User join(UserJoinForm userJoinForm) {
        if(userRepository.existsByUsername(userJoinForm.getUsername()))
            return null;
        return userRepository.save(userJoinForm.toEntity());
    }


    public User login(UserLoginForm userLoginForm) {
        Optional<User> optionalUser = userRepository.findByUsername(userLoginForm.getUsername());
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        if (!user.getPassword().equals(userLoginForm.getPassword())) return null;
        return user;
    }
}
