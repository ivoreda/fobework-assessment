package com.muzan.musicbookingapp.controller;

import com.muzan.musicbookingapp.dto.LoginRequest;
import com.muzan.musicbookingapp.dto.UserDto;
import com.muzan.musicbookingapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getAuthenticatedUser () {
        return ResponseEntity.ok(userService.getCurrentUserDto());
    }
}
