package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "avatars")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AvatarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hat_id")
    private ItemEntity hat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eyewear_id")
    private ItemEntity eyewear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "top_id")
    private ItemEntity top;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bottom_id")
    private ItemEntity bottom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoes_id")
    private ItemEntity shoes;
}
