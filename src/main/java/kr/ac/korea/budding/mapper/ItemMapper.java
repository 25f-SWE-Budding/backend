package kr.ac.korea.budding.mapper;

import kr.ac.korea.budding.dto.ItemResponseDto;
import kr.ac.korea.budding.entity.ItemEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemResponseDto toDto(ItemEntity item);

    List<ItemResponseDto> toDto(List<ItemEntity> items);
}
