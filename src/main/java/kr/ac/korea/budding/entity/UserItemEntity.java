package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private ItemEntity item;
}
