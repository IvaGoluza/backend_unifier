package hr.fer.unifier.backend.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "RECENSION")
public class Recension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recensionId;

    @ManyToOne
    @JoinColumn(name = "DEAL_ID", nullable = false)
    private Deal deal;

    @Column(nullable = false)
    private String recension;
}
