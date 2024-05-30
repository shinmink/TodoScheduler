package com.sparta.todoscheduler.controller;

import com.sparta.todoscheduler.dto.LoginRequestDto;
import com.sparta.todoscheduler.dto.SignupRequestDto;
import com.sparta.todoscheduler.dto.UserInfoDto;
import com.sparta.todoscheduler.entity.UserRoleEnum;
import com.sparta.todoscheduler.jwt.JwtUtil;
import com.sparta.todoscheduler.security.UserDetailsImpl;
import com.sparta.todoscheduler.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            String token = jwtUtil.createToken(username);
            return ResponseEntity.ok().header("Authorization", "Bearer " + token).body("로그인 성공");
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body("회원을 찾을 수 없습니다.");
        }
    }
}