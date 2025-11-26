package kr.ac.korea.budding.dto;

import kr.ac.korea.budding.enums.ItemSlots;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ItemResponseDto {

    private Long id;

    private ItemSlots slot;

    private String name;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
