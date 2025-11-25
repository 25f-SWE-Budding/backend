package kr.ac.korea.budding.dto;

import kr.ac.korea.budding.entity.AvatarEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponseDto {

    private Integer id;

    private String email;

    private String pw;

    private String nickname;

    private Integer points;

    private Boolean dailyCheckInStatus;
}
