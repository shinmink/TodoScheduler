package com.sparta.todoscheduler.service;
import com.sparta.todoscheduler.dto.SignupRequestDto;
import com.sparta.todoscheduler.entity.User;
import com.sparta.todoscheduler.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void signup(SignupRequestDto request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        User user = new User();
        user.setNickname(request.getNickname());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole("USER");

        userRepository.save(user);
    }
}
