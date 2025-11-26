package kr.ac.korea.budding.mapper;

import kr.ac.korea.budding.dto.ChallengeRequestDto;
import kr.ac.korea.budding.dto.ChallengeResponseDto;
import kr.ac.korea.budding.entity.ChallengeEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {

    ChallengeResponseDto toDto(ChallengeEntity challenge);

    List<ChallengeResponseDto> toDto(List<ChallengeEntity> challenges);

    ChallengeEntity toEntity(ChallengeRequestDto challenge);
}
