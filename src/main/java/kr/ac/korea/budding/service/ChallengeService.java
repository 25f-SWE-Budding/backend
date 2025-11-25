package kr.ac.korea.budding.service;

import jakarta.transaction.Transactional;
import kr.ac.korea.budding.dto.ChallengeRequestDto;
import kr.ac.korea.budding.dto.ChallengeResponseDto;
import kr.ac.korea.budding.entity.ChallengeEntity;
import kr.ac.korea.budding.mapper.ChallengeMapper;
import kr.ac.korea.budding.repository.ChallengeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeMapper challengeMapper;

    @Transactional
    public ChallengeResponseDto createSchedule(ChallengeRequestDto req) {
        ChallengeEntity challenge = challengeMapper.toEntity(req);

        challengeRepository.save(challenge);

        return challengeMapper.toDto(challenge);
    }
}
