package hr.fer.unifier.backend.model;

import hr.fer.unifier.backend.model.enums.Sender;
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
    @Column(name = "id")
    private Long id;

    @Column(length = 1000)
    private String recension;

    @Column(length = 1000)
    private String note;

    @Column
    private boolean accepted;

    @Column
    @Enumerated(EnumType.STRING)
    private Sender sender;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    @ManyToOne
    @JoinColumn(name = "advert_id")
    private Advert advert;


}
