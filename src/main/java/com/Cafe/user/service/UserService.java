package com.Cafe.user.service;

import com.Cafe.user.common.UserJoinDto;
import com.Cafe.user.common.UserLoginDto;
import com.Cafe.user.entity.User;
import com.Cafe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User join(UserJoinDto userJoinDto) {
        if(userRepository.existsByUsername(userJoinDto.getUsername()))
            return null;
        return userRepository.save(userJoinDto.toEntity());
    }


    public User login(UserLoginDto userLoginDto) {
        Optional<User> optionalUser = userRepository.findByUsername(userLoginDto.getUsername());
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        if (!user.getPassword().equals(userLoginDto.getPassword())) return null;
        return user;
    }

    public User getUserById(Long userId) {
        if (userId == null) return null;
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(null);
    }

    public void updateUserGrade(User user) {
        long totalAmount = user.getTotalAmount();
        if (totalAmount >= 50000) {
            user.setGrade("Gold");
        } else if (totalAmount >= 30000) {
            user.setGrade("Silver");
        } else if (totalAmount >= 10000) {
            user.setGrade("Bronze");
        } else {
            user.setGrade(null);
        }
        userRepository.save(user);
    }

}
