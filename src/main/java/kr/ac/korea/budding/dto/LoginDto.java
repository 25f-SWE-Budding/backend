package kr.ac.korea.budding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {

    @Schema(example = "sebin")
    private String email;

    @Schema(example = "tpqls")
    private String pw;
}
