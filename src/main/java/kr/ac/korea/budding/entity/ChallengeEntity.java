package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Entity
@Table(name = "challenges")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChallengeEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable = false)
    private String category;

    private String goal;    // 챌린지 목표 설명

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Long targetCount;    // 전체 횟수

    private String rewardName;
    private String rewardUrl;

    private String notion;  // 메모
}
