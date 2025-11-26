package kr.ac.korea.budding.service;

import jakarta.transaction.Transactional;
import kr.ac.korea.budding.dto.FriendshipResponseDto;
import kr.ac.korea.budding.dto.UserResponseDto;
import kr.ac.korea.budding.entity.FriendshipEntity;
import kr.ac.korea.budding.entity.UserEntity;
import kr.ac.korea.budding.enums.FriendshipStatus;
import kr.ac.korea.budding.mapper.FriendshipMapper;
import kr.ac.korea.budding.mapper.UserMapper;
import kr.ac.korea.budding.repository.FriendshipRepository;
import kr.ac.korea.budding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final UserRepository userRepository;
    private final FriendshipMapper friendshipMapper;
    private final UserMapper userMapper;

    // 친구 등록
    @Transactional
    public FriendshipResponseDto createFriendship(Integer user1_id, Integer user2_id) {

        // 유저 번호 정렬, user1_id < user2_id
        Integer minId = Math.min(user1_id, user2_id);
        Integer maxId = Math.max(user1_id, user2_id);

        // 자기 자신과는 친구 추가 불가
        if (minId.equals(maxId)) {
            throw new RuntimeException(String.format("cannot create friendship with yourself, id %d", minId));
        }

        // 이미 친구인지 확인
        FriendshipEntity check = friendshipRepository.findByUser1IdAndUser2Id(minId, maxId);
        if (check != null) {
            throw new RuntimeException(String.format("Friendship with id %d and id %d already exists", minId, maxId));
        }

        // user1_id < user2_id
        UserEntity user1 = userRepository.findById(minId).orElseThrow(
                () -> new RuntimeException(String.format("User with id %d not found", minId))
        );

        UserEntity user2 = userRepository.findById(maxId).orElseThrow(
                () -> new RuntimeException(String.format("User with id %d not found", maxId))
        );


        FriendshipEntity friendship = new FriendshipEntity();
        friendship.setUser1(user1);
        friendship.setUser2(user2);
        friendship.setStatus(FriendshipStatus.ACCEPTED);
        friendship.setCreatedAt(LocalDateTime.now());
        friendshipRepository.save(friendship);

        return friendshipMapper.toDto(friendship);
    }

    // 내 친구 조회
    @Transactional
    public List<UserResponseDto> findMyFriends(Integer userId) {
        List<FriendshipEntity> friends = friendshipRepository.findByUser1IdOrUser2Id(userId, userId);

        // 상대방만 뽑아서 List로 만든다.
        return friends.stream()
                .map(f -> f.getUser1().getId().equals(userId) ? f.getUser2() : f.getUser1())
                .map(userMapper::toDto)
                .toList();
    }
}
