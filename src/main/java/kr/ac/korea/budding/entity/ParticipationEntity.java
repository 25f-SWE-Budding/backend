package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import kr.ac.korea.budding.enums.ParticipationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "participations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ParticipationEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ChallengeEntity challenge;

    @Column
    private LocalDateTime joinedAt;

    @Enumerated
    private ParticipationStatus status;
}
