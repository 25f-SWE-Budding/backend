package kr.ac.korea.budding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import kr.ac.korea.budding.enums.ItemSlots;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemRequestDto {

    @Schema(example = "HAT")
    private ItemSlots slot;

    @Schema(example = "hat_1")
    private String name;

    @Schema(example = "url_hat_1")
    private String imageUrl;
}
