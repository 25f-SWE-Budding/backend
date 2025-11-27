package kr.ac.korea.budding.mapper;

import kr.ac.korea.budding.dto.FriendshipResponseDto;
import kr.ac.korea.budding.entity.FriendshipEntity;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FriendshipMapper {

    @Mapping(source="user1.id", target="user1Id")
    @Mapping(source="user2.id", target="user2Id")
    FriendshipResponseDto toDto(FriendshipEntity friendship);

    List<FriendshipResponseDto> toDto(List<FriendshipEntity> friendships);
}
