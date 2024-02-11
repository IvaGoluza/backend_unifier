package hr.fer.unifier.backend.db.entity;

import hr.fer.unifier.backend.db.user.entity.Organization;
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
@Table(name = "ADDRESS")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String townName;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private String postcode;

    @OneToOne(mappedBy = "address")
    private Organization organization;

}
