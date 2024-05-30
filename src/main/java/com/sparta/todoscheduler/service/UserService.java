package com.sparta.todoscheduler.service;

import com.sparta.todoscheduler.dto.LoginRequestDto;
import com.sparta.todoscheduler.dto.SignupRequestDto;
import com.sparta.todoscheduler.entity.User;
import com.sparta.todoscheduler.entity.UserRoleEnum;
import com.sparta.todoscheduler.jwt.JwtUtil;
import com.sparta.todoscheduler.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public void signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        UserRoleEnum role = signupRequestDto.isAdmin() ? UserRoleEnum.ADMIN : UserRoleEnum.USER;

        User user = new User(username, password, role);
        userRepository.save(user);
    }

    public String login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return jwtUtil.getUsernameFromToken(username);
    }
}
