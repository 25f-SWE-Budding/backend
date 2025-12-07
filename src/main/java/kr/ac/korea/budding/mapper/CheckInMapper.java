package kr.ac.korea.budding.mapper;

import kr.ac.korea.budding.dto.CheckInResponseDto;
import kr.ac.korea.budding.entity.CheckInEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CheckInMapper {

    @Mapping(source = "user.id", target = "user_id")
    CheckInResponseDto toDto(CheckInEntity checkIn);
}
