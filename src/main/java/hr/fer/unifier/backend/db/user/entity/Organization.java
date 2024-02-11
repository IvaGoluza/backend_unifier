package hr.fer.unifier.backend.db.user.entity;

import hr.fer.unifier.backend.db.entity.Address;
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
@Table(name = "ORGANIZATION")
public class Organization extends User{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String oib;

    @Column(nullable = false)
    private String type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private String url;
}
