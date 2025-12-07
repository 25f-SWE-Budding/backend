package kr.ac.korea.budding.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class ChallengeResponseDto {
    private Long id;
    private String name;
    private String category;
    private String goal;

    private Date startDate;
    private Date endDate;

    private Integer targetCount;

    private String rewardName;
    private String rewardUrl;

    private String notion;
}
