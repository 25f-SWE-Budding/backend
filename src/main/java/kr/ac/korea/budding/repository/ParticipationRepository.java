package kr.ac.korea.budding.repository;

import kr.ac.korea.budding.dto.ParticipantResponseDto;
import kr.ac.korea.budding.entity.ChallengeEntity;
import kr.ac.korea.budding.entity.ParticipationEntity;
import kr.ac.korea.budding.enums.ParticipationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationRepository extends JpaRepository<ParticipationEntity, Long> {
    List<ParticipationEntity> findByUserId(Long userId);

    List<ParticipationEntity> findByUserIdAndStatus(Long userId, ParticipationStatus status);

    List<ParticipationEntity> challenge(ChallengeEntity challenge);

    Long countByChallenge_Id(Long challengeId);

    List<ParticipationEntity> findAllByChallenge_Id(Long challengeId);

    long countByChallengeId(Long challengeId);

    List<ParticipationEntity> findByChallengeIdAndStatus(Long challengeId, ParticipationStatus status);
}
