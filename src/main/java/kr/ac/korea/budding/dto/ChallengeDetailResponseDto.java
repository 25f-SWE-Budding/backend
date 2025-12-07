package kr.ac.korea.budding.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ChallengeDetailResponseDto {

    private Long id;
    private String name;
    private String category;
    private String goal;         // 챌린지 목표 설명

    private LocalDate startDate;
    private LocalDate endDate;

    private long succeedDays;
    private long leftDays;

    private String rewardName;
    private String rewardUrl;

    private String notion;       // 메모

    private List<participantDto> participants;

    @Getter
    @Builder
    public static class participantDto {
        private Long id;
        private String Nickname;
        private Long avatarId;
    }
}

