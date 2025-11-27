package kr.ac.korea.budding.repository;

import kr.ac.korea.budding.entity.CertificationChallengeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificationChallengeRepository extends JpaRepository<CertificationChallengeEntity, Long> {
}
