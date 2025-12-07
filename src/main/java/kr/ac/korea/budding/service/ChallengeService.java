package kr.ac.korea.budding.service;

import kr.ac.korea.budding.dto.ParticipantResponseDto;
import org.springframework.transaction.annotation.Transactional;
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

        ChallengeResponseDto dto = challengeMapper.toDto(challenge);
        dto.setParticipants(1);     // 방금 만든거니까 1로 하드코딩(기술 부채..)

        return dto;
    }

    // 내가 진행 중인 챌린지들 확인하기
    @Transactional
    public List<ChallengeResponseDto> getMyChallenges(Long userId, ParticipationStatus status) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(String.format("user with id: %d not found", userId))
        );

        List<ParticipationEntity> participations = participationRepository.findByUserIdAndStatus(userId, status);

        return participations.stream()
                .map(ParticipationEntity::getChallenge)
                .distinct()
                .map(challenge -> {
                    ChallengeResponseDto dto = challengeMapper.toDto(challenge);

                    long participantCount = participationRepository.countByChallengeId(challenge.getId());

                    dto.setParticipants((int) participantCount);
                    return dto;
                })
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

    // 챌린지의 인원 수 확인
    @Transactional(readOnly = true)
    public Long countChallengeParticipants(Long challengeId){

        return participationRepository.countByChallenge_Id(challengeId);
    }

    // 챌린지 참가자 확인
    @Transactional
    public List<ParticipantResponseDto> getChallengeParticipants(Long challengeId){

        List<ParticipationEntity> participations = participationRepository.findAllByChallenge_Id(challengeId);

        return participations.stream()
                .map(p -> new ParticipantResponseDto(
                        p.getUser().getId(),
                        p.getUser().getNickname()
                ))
                .toList();
    }
}
