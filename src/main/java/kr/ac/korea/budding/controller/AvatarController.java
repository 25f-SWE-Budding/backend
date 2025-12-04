package kr.ac.korea.budding.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.ac.korea.budding.dto.AvatarRequestDto;
import kr.ac.korea.budding.dto.AvatarResponseDto;
import kr.ac.korea.budding.dto.UserResponseDto;
import kr.ac.korea.budding.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    @PostMapping("/{userId}/setAvatar")
    @Operation(summary = "내 아바타 설정하기")
    public AvatarResponseDto setAvatar(@PathVariable Long userId, @RequestBody AvatarRequestDto dto) {
        return avatarService.setAvatar(userId, dto);
    }

    @GetMapping("/{userId}/getAvatar")
    @Operation(summary = "내 아바타 확인하기")
    public AvatarResponseDto getAvatar(@PathVariable Long userId) {
        return avatarService.getAvatarByUserId(userId);
    }
}
