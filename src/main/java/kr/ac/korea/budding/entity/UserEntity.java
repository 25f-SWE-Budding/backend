package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String pw;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private Integer points;

    @Column(nullable = false)
    private Boolean dailyCheckInStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "avatar_id", nullable = false)
    private AvatarEntity avatar;
}
