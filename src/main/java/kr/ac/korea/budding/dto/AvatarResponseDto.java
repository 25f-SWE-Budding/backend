package kr.ac.korea.budding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvatarResponseDto {

    private Long id;

    @Schema(example = "default_hat")
    private String hat;

    @Schema(example = "default_eyewear")
    private String eyewear;

    @Schema(example = "default_top")
    private String top;

    @Schema(example = "default_bottom")
    private String bottom;

    @Schema(example = "default_shoes")
    private String shoes;
}
