package kr.ac.korea.budding.mapper;

import kr.ac.korea.budding.dto.UserResponseDto;
import kr.ac.korea.budding.entity.UserEntity;
import org.apache.catalina.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "avatar.id", target = "avatarId")
    UserResponseDto toDto(UserEntity user);

    List<UserResponseDto> toDto(List<UserEntity> users);
}
