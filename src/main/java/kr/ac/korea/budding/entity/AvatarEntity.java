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

    @Column
    private String hat;

    @Column
    private String eyewear;

    @Column
    private String top;

    @Column
    private String bottom;

    @Column
    private String shoes;
}
