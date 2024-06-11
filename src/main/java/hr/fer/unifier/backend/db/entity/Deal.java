package hr.fer.unifier.backend.db.entity;

import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.Sender;
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
@Table(name = "DEAL")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dealId;

    @Column
    private Boolean accepted = null;

    @Column
    @Enumerated(EnumType.STRING)
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "REQUEST_ID")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "ADVERT_ID")
    private Advert advert;

    @Column
    private String message;

    @Column(name = "VOLUNTEER_POSITION")
    private String volunteerPosition;

    @Column(name = "VOLUNTEER_WORK_DESCRIPTION", length = 2048)
    private String volunteerWorkDescription;

    @ManyToOne
    @JoinColumn(name = "SENDER_ID")
    private User senderId;

    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID")
    private User receiver;
}
