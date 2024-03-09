package hr.fer.unifier.backend.db.entity;

import hr.fer.unifier.backend.db.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "RECENSION")
public class    Recension {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recensionId;

    @ManyToOne
    @JoinColumn(name = "DEAL_ID", nullable = false)
    private Deal deal;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "VOLUNTEER_WORK_DESCRIPTION")
    private String volunteerWorkDescription;

    @Column(name = "VOLUNTEER_POSITION")
    private String volunteerPosition;

    @ManyToOne
    @JoinColumn(name = "REVIEWING_USER_ID", nullable = false)
    private User reviewingUser;

    @Column(nullable = false)
    private String recension;
}
