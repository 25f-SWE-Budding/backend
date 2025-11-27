package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "certifications")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CertificationChallengeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private ChallengeEntity challenge;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String imagePath;

    private String memo;
}
