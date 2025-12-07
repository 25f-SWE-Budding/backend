package kr.ac.korea.budding.repository;

import kr.ac.korea.budding.entity.ChallengeCheckInEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ChallengeCheckInRepository extends JpaRepository<ChallengeCheckInEntity, Long> {

    // 특정 유저의 성공 일수
    long countByChallengeIdAndUserIdAndDateBetween(
            Long challengeId,
            Long userId,
            LocalDate startDate,
            LocalDate endDate
    );

    // 필요하다면: 챌린지 전체(모든 유저) 성공 기록 수
    long countByChallengeIdAndDateBetween(
            Long challengeId,
            LocalDate startDate,
            LocalDate endDate
    );
}
