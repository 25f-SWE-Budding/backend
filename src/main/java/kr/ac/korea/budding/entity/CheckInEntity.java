package kr.ac.korea.budding.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.EnableMBeanExport;

import java.time.LocalDate;

@Entity
@Table(
        name = "checkIn",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_user_checkIn",
                        columnNames = {"user_id", "date"}
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckInEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private LocalDate date;

    private Boolean status;
}
