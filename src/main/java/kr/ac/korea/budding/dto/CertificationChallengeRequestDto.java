package kr.ac.korea.budding.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CertificationChallengeRequestDto {

    private Long userId;

    private Long challengeId;

    private MultipartFile image;
    private String memo;
}
