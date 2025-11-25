package kr.ac.korea.budding.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDto {

    @Schema(example = "sebin")
    private String email;

    @Schema(example = "tpqls")
    private String pw;

    @Schema(example = "user_sebin")
    private String nickname;
    
    // 나머지는 시스템이 자동 생성할 부분
}
