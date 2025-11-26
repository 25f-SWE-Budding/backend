package kr.ac.korea.budding.mapper;

import kr.ac.korea.budding.dto.AvatarResponseDto;
import kr.ac.korea.budding.entity.AvatarEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring") // Spring Bean으로 등록
public interface AvatarMapper {
    // 단일 객체 매핑
    AvatarResponseDto toDto(AvatarEntity entity);

    // List 객체 매핑
    List<AvatarResponseDto> toDto(List<AvatarEntity> entities);
}