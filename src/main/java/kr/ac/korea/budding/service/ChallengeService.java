package kr.ac.korea.budding.service;

import jakarta.transaction.Transactional;
import kr.ac.korea.budding.dto.CertificationChallengeRequestDto;
import kr.ac.korea.budding.dto.CertificationChallengeResponseDto;
import kr.ac.korea.budding.dto.ChallengeRequestDto;
import kr.ac.korea.budding.dto.ChallengeResponseDto;
import kr.ac.korea.budding.entity.CertificationChallengeEntity;
import kr.ac.korea.budding.entity.ChallengeEntity;
import kr.ac.korea.budding.entity.ParticipationEntity;
import kr.ac.korea.budding.entity.UserEntity;
import kr.ac.korea.budding.enums.ParticipationStatus;
import kr.ac.korea.budding.mapper.CertificationChallengeMapper;
import kr.ac.korea.budding.mapper.ChallengeMapper;
import kr.ac.korea.budding.repository.CertificationChallengeRepository;
import kr.ac.korea.budding.repository.ChallengeRepository;
import kr.ac.korea.budding.repository.ParticipationRepository;
import kr.ac.korea.budding.repository.UserRepository;
import kr.ac.korea.budding.utils.SupabaseStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;
    private final ParticipationRepository participationRepository;
    private final UserRepository userRepository;

    private final CertificationChallengeRepository certificationChallengeRepository;
    private final SupabaseStorageUtil supabaseStorageUtil;
    private final CertificationChallengeMapper certificationChallengeMapper;

    // 챌린지 만들기
    @Transactional
    public ChallengeResponseDto createChallenge(ChallengeRequestDto req, Long userId) {
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
    public List<ChallengeResponseDto> getMyChallenges(Long userId, ParticipationStatus status) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(String.format("user with id: %d not found", userId))
        );

        List<ParticipationEntity> participations = participationRepository.findByUserIdAndStatus(userId, status);

        return participations.stream()
                .map(ParticipationEntity::getChallenge)
                .map(challengeMapper::toDto)
                .toList();
    }

    // 챌린지에 인증 사진 업로드
    @Transactional
    public CertificationChallengeResponseDto certificationChallenge(Long userId, Long challengeId, MultipartFile image, String memo) {

        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException(String.format("user with id: %d not found", userId))
        );

        ChallengeEntity challenge = challengeRepository.findById(challengeId).orElseThrow(
                () -> new RuntimeException(String.format("challenge with id: %d not found", challengeId))
        );

        if (image == null || image.isEmpty()) {
            throw new RuntimeException("이미지 파일이 비어 있습니다");
        }

        String date = LocalDate.now().toString();
        String uuid = UUID.randomUUID().toString();
        String path = String.format("user-%d/challenge-%d/%s/%s", user.getId(), challenge.getId(), date, uuid);

        String url = supabaseStorageUtil.uploadFileToBucket(image, path);

        CertificationChallengeEntity certification = CertificationChallengeEntity.builder()
                .user(user)
                .challenge(challenge)
                .createdAt(LocalDateTime.now())
                .imagePath(url)
                .memo(memo)
                .build();

        certificationChallengeRepository.save(certification);

        return certificationChallengeMapper.toDto(certification);
    }

}
