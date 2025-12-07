package kr.ac.korea.budding.service;

import kr.ac.korea.budding.dto.*;
import kr.ac.korea.budding.entity.*;
import kr.ac.korea.budding.mapper.CheckInChallengeMapper;
import kr.ac.korea.budding.repository.*;
import org.springframework.transaction.annotation.Transactional;
import kr.ac.korea.budding.enums.ParticipationStatus;
import kr.ac.korea.budding.mapper.CertificationChallengeMapper;
import kr.ac.korea.budding.mapper.ChallengeMapper;
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
    private final ChallengeCheckInRepository challengeCheckInRepository;
    private final CheckInChallengeMapper checkInChallengeMapper;

    private final CertificationChallengeRepository certificationChallengeRepository;
    private final SupabaseStorageUtil supabaseStorageUtil;
    private final CertificationChallengeMapper certificationChallengeMapper;

    // 챌린지 만들기
    @Transactional
    public ChallengeResponseDto createChallenge(Long userId, ChallengeRequestDto req) {
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
                .map(challengeMapper::toDto)
                .toList();
    }

    // 챌린지에 인증 사진 업로드
    @Transactional
    public CheckInChallengeResponseDto checkInChallenge(Long challengeId, Long userId, MultipartFile image, String memo) {

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

        ChallengeCheckInEntity checkIn = ChallengeCheckInEntity.builder()
                .user(user)
                .challenge(challenge)
                .createdAt(LocalDateTime.now())
                .imagePath(url)
                .memo(memo)
                .build();

        challengeCheckInRepository.save(checkIn);

        return checkInChallengeMapper.toDto(checkIn);
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

    // 유저별 챌린지 상세정보
    @Transactional(readOnly = true)
    public ChallengeDetailResponseDto getChallengeDetailByUser(Long challengeId, Long userId){
        ChallengeEntity challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found: " + challengeId));

        LocalDate today = LocalDate.now();
        LocalDate start = challenge.getStartDate();
        LocalDate end = challenge.getEndDate();

        LocalDate endForCount = today.isAfter(end) ? end : today;

        long succeedDays = challengeCheckInRepository
                .countByChallengeIdAndUserIdAndDateBetween(
                        challengeId,
                        userId,
                        start,
                        endForCount
                );

        long leftDays = challenge.getTargetCount() - succeedDays;

        // 참여자 목록
        List<ParticipationEntity> participations =
                participationRepository.findByChallengeIdAndStatus(challengeId, ParticipationStatus.ACTIVE);

        List<ChallengeDetailResponseDto.participantDto> participants = participations.stream()
                .map(p -> {
                    UserEntity u = p.getUser();
                    return ChallengeDetailResponseDto.participantDto.builder()
                            .id(u.getId())
                            .Nickname(u.getNickname())
                            .avatarId(u.getAvatar().getId())
                            .build();
                })
                .toList();

        return ChallengeDetailResponseDto.builder()
                .id(challenge.getId())
                .name(challenge.getName())
                .category(challenge.getCategory())
                .startDate(start)
                .endDate(end)
                .rewardName(challenge.getRewardName())
                .rewardUrl(challenge.getRewardUrl())
                .participants(participants)
                .succeedDays(succeedDays)
                .leftDays(leftDays)
                .goal(challenge.getGoal())
                .notion(challenge.getNotion())
                .build();

    }

    // 챌린지 참여하기
    @Transactional
    public ChallengeResponseDto participateChallenge(Long challengeId, Long userId) {
        ChallengeEntity challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found: " + challengeId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found: " + userId));

        ParticipationEntity p = ParticipationEntity.builder()
                .user(user)
                .challenge(challenge)
                .joinedAt(LocalDateTime.now())
                .status(ParticipationStatus.ACTIVE)
                .build();

        participationRepository.save(p);

        return challengeMapper.toDto(challenge);
    }

}
