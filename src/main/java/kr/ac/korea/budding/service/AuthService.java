package kr.ac.korea.budding.service;

import jakarta.transaction.Transactional;
import kr.ac.korea.budding.dto.LoginDto;
import kr.ac.korea.budding.dto.RegisterDto;
import kr.ac.korea.budding.dto.UserResponseDto;
import kr.ac.korea.budding.entity.AvatarEntity;
import kr.ac.korea.budding.entity.UserEntity;
import kr.ac.korea.budding.mapper.UserMapper;
import kr.ac.korea.budding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDto register(RegisterDto req) {

        // 기본 아바타로 설정(item은 null)
        AvatarEntity default_avatar = AvatarEntity.builder().build();

        // 유저 객체 만들기
        UserEntity user = UserEntity.builder()
                .email(req.getEmail())
                .pw(req.getPw())
                .nickname(req.getNickname())
                .points(0)
                .avatar(default_avatar)
                .build();

        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public UserResponseDto login(LoginDto req) {

        // email이 있는지 확인
        UserEntity user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException(String.format("user with email: %s does not exists", req.getEmail())));

        // pw 확인
        if ((user.getPw()).equals(req.getPw())) {
            return userMapper.toDto(user);
        } else {
            throw new RuntimeException("password wrong");
        }


    }
}
