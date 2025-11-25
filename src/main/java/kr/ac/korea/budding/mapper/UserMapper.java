package kr.ac.korea.budding.mapper;

import kr.ac.korea.budding.dto.UserResponseDto;
import kr.ac.korea.budding.entity.UserEntity;
import org.apache.catalina.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(UserEntity user);

    List<UserResponseDto> toDto(List<UserEntity> users);
}
