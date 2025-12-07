package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "challenge_check_in",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"challenge_id", "user_id", "date"}
                )
        })
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChallengeCheckInEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 챌린지인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private ChallengeEntity challenge;

    // 어떤 유저의 기록인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // 어느 날짜 기록인지 (하루 1개)
    @Column(nullable = false)
    private LocalDate date;

    // 인증 이미지
    private String imagePath;

    // 메모
    private String memo;

    private LocalDateTime createdAt;
}

