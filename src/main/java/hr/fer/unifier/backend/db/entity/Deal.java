package hr.fer.unifier.backend.db.entity;

import hr.fer.unifier.backend.db.user.entity.User;
import hr.fer.unifier.backend.enums.Sender;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
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
    private boolean accepted = false;

    @Column
    @Enumerated(EnumType.STRING)
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "advert_id")
    private Advert advert;

    @Column
    private String message;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User senderId;
}
