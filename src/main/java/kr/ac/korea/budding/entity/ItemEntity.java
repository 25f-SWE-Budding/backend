package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import kr.ac.korea.budding.enums.ItemSlots;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private ItemSlots slot;

    @Column(nullable = false, unique = true)
    private String name;

    private Integer price;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
