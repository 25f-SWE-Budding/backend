package kr.ac.korea.budding.dto;

import lombok.*;

@Getter
@Setter
@Builder
public class UserPointResponseDto {

    private Integer userId;

    private Integer points;
}
