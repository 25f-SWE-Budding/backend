package kr.ac.korea.budding.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "challenge_check_in",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"challenge_id", "user_id", "date"}
                )
        })
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

    // 선택: 인증 이미지나 메모가 필요하면
    private String proofImageUrl;
    private String memo;

    private LocalDateTime createdAt;
}

