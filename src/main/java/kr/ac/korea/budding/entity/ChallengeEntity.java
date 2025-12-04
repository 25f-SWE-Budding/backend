package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

//    @Column(nullable = false)
//    private Integer targetCount;
//
//    @Column(nullable = false)
//    private Integer currentCertCount;

    private String rewardName;

    private String rewardUrl;

}
