package kr.ac.korea.budding.controller;

import kr.ac.korea.budding.dto.LoginDto;
import kr.ac.korea.budding.dto.RegisterDto;
import kr.ac.korea.budding.dto.UserResponseDto;
import kr.ac.korea.budding.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody RegisterDto dto) {
        return authService.register(dto);
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody LoginDto dto) {
        return authService.login(dto);
    }
}
