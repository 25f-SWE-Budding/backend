package kr.ac.korea.budding.controller;

import kr.ac.korea.budding.dto.FriendshipResponseDto;
import kr.ac.korea.budding.dto.UserResponseDto;
import kr.ac.korea.budding.repository.FriendshipRepository;
import kr.ac.korea.budding.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friendships")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("")
    public FriendshipResponseDto createFriendship(
            @RequestParam Integer user1_id,
            @RequestParam Integer user2_id
    ){
        return friendshipService.createFriendship(user1_id, user2_id);
    }

    @GetMapping("/{userId}")
    public List<UserResponseDto> findMyFriends(
            @PathVariable Integer userId
    ) {
        return friendshipService.findMyFriends(userId);
    }
}
