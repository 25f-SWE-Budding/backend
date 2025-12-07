package kr.ac.korea.budding.mapper;

import kr.ac.korea.budding.dto.CertificationChallengeRequestDto;
import kr.ac.korea.budding.dto.CheckInChallengeResponseDto;
import kr.ac.korea.budding.entity.CertificationChallengeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CertificationChallengeMapper {

    @Mapping(source="user.id", target = "userId")
    @Mapping(source="challenge.id", target = "challengeId")
    CheckInChallengeResponseDto toDto(CertificationChallengeEntity certificationChallenge);

    List<CertificationChallengeRequestDto> toDto(List<CertificationChallengeEntity> certificationChallenges);
}
