package hr.fer.unifier.backend.db.user.entity;

import hr.fer.unifier.backend.db.entity.Advert;
import hr.fer.unifier.backend.db.entity.Request;
import hr.fer.unifier.backend.enums.Role;
import hr.fer.unifier.backend.enums.UserType;
import hr.fer.unifier.backend.enums.VolunteerCenter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String mobilePhone;

    @Column(length = 1000)
    private String profileDescription;

    @Column(nullable = false)
    private String password;

    @Lob
    private byte[] file;

    private boolean blocked = false;

    private boolean isApproved;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private VolunteerCenter volunteerCenter;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Request> requests;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Advert> adverts;
}
