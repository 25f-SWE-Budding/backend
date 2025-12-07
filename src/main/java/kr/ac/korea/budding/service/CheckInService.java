package kr.ac.korea.budding.service;

import kr.ac.korea.budding.dto.CheckInResponseDto;
import kr.ac.korea.budding.entity.CheckInEntity;
import kr.ac.korea.budding.entity.UserEntity;
import kr.ac.korea.budding.mapper.CheckInMapper;
import kr.ac.korea.budding.repository.CheckInRepository;
import kr.ac.korea.budding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CheckInService {
    
    private final UserRepository userRepository;
    private final CheckInRepository checkInRepository;
    private final CheckInMapper checkInMapper;
    
    // 출석 체크
    public CheckInResponseDto checkIn(Long userId) {

        LocalDate date = LocalDate.now();

        // 유저가 있는지 확인
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException(String.format("user with id %d not found", userId))
        );

        // 이미 출석했는지 확인
        CheckInEntity ch = checkInRepository.findByUserIdAndDate(userId, date);

        if (ch == null || ch.getStatus() == false) {
            CheckInEntity checkIn = CheckInEntity.builder()
                    .user(user)
                    .date(date)
                    .status(true)
                    .build();
            checkInRepository.save(checkIn);

            return checkInMapper.toDto(checkIn);
        } else {
            throw new RuntimeException(String.format("user with id %d already checked in today", userId));
        }
    }

    // 오늘 출석했는지 확인
    public Boolean IscheckIn(Long userId) {
        LocalDate date = LocalDate.now();

        // 유저가 있는지 확인
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException(String.format("user with id %d not found", userId))
        );

        return checkInRepository.existsByUserIdAndDate(userId, date);
    }
}
