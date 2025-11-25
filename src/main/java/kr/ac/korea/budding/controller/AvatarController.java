package kr.ac.korea.budding.controller;

import kr.ac.korea.budding.dto.AvatarRequestDto;
import kr.ac.korea.budding.dto.AvatarResponseDto;
import kr.ac.korea.budding.dto.UserResponseDto;
import kr.ac.korea.budding.service.AvatarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/avatar")
public class AvatarController {

    private final AvatarService avatarService;

    @PostMapping("/{userId}/setAvatar")
    public AvatarResponseDto setAvatar(@PathVariable Integer userId, @RequestBody AvatarRequestDto dto) {
        return avatarService.setAvatar(userId, dto);
    }

    @GetMapping("/{userId}/getAvatar")
    public AvatarResponseDto getAvatar(@PathVariable Integer userId) {
        return avatarService.getAvatarByUserId(userId);
    }
}
