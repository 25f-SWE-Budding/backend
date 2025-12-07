package kr.ac.korea.budding.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CheckInChallengeResponseDto {

    private Long challengeId;

    private Long userId;

    private LocalDate date;

    private String imagePath;

    private String memo;

    private LocalDateTime createdAt;
}
