package kr.ac.korea.budding.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CertificationChallengeResponseDto {

    private Long userId;

    private Long challengeId;

    private LocalDateTime createdAt;

    private String imagePath;

    private String memo;
}
