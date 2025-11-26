package kr.ac.korea.budding.service;

import jakarta.transaction.Transactional;
import kr.ac.korea.budding.dto.ChallengeRequestDto;
import kr.ac.korea.budding.dto.ChallengeResponseDto;
import kr.ac.korea.budding.entity.ChallengeEntity;
import kr.ac.korea.budding.entity.ParticipationEntity;
import kr.ac.korea.budding.entity.UserEntity;
import kr.ac.korea.budding.enums.ParticipationStatus;
import kr.ac.korea.budding.mapper.ChallengeMapper;
import kr.ac.korea.budding.repository.ChallengeRepository;
import kr.ac.korea.budding.repository.ParticipationRepository;
import kr.ac.korea.budding.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;

    // 챌린지 만들기
    @Transactional
    public ChallengeResponseDto createChallenge(ChallengeRequestDto req, Integer userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(String.format("user with id: %d not found", userId))
        );

        ChallengeEntity challenge = challengeMapper.toEntity(req);

        challengeRepository.save(challenge);

        ParticipationEntity participation = ParticipationEntity.builder()
                .user(user)
                .challenge(challenge)
                .joinedAt(LocalDateTime.now())
                .status(ParticipationStatus.ACTIVE)
                .build();

        participationRepository.save(participation);

        return challengeMapper.toDto(challenge);
    }

    // 내 챌린지 보기
    @Transactional
    public List<ChallengeResponseDto> getMyChallenges(Integer userId, ParticipationStatus status) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(String.format("user with id: %d not found", userId))
        );

        List<ParticipationEntity> participations = participationRepository.findByUserIdAndStatus(userId, status);

        return participations.stream()
                .map(ParticipationEntity::getChallenge)
                .map(challengeMapper::toDto)
                .toList();
    }

}
