package kr.ac.korea.budding.dto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ChallengeRequestDto {

    @Schema(example="주 3회 헬스장 가기")
    private String name;

    @Schema(example="운동")
    private String category;

    @Schema(example="2026-01-01")
    private Date startDate;

    @Schema(example="2026-01-15")
    private Date endDate;

//    @Schema(example="9")
//    private Integer targetCount;
//
//    @Schema(example="1")
//    private Integer currentCertCount;

    @Schema(example = "로봇 청소기")
    private String rewardName;

    @Schema(example = "robot_vacuum_ex_url")
    private String rewardUrl;
}
